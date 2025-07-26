package com.example.foorball_manager.service;

import com.example.foorball_manager.dto.TransferDto;
import com.example.foorball_manager.dto.TransferResponseDto;
import com.example.foorball_manager.entity.Player;
import com.example.foorball_manager.entity.Team;
import com.example.foorball_manager.entity.Transfer;
import com.example.foorball_manager.mapper.TransferMapper;
import com.example.foorball_manager.repository.PlayerRepository;
import com.example.foorball_manager.repository.TeamRepository;
import com.example.foorball_manager.repository.TransferRepository;
import com.example.foorball_manager.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TransferMapper transferMapper;

    @InjectMocks
    private TransferServiceImpl transferService;

    private Player player;
    private Team fromTeam;
    private Team toTeam;
    private Transfer transfer;
    private TransferDto transferDto;
    private TransferResponseDto transferResponseDto;

    @BeforeEach
    void setUp() {
        fromTeam = new Team();
        fromTeam.setId(1L);
        fromTeam.setName("Barcelona");

        toTeam = new Team();
        toTeam.setId(2L);
        toTeam.setName("PSG");
        toTeam.setCommission(0.1);
        toTeam.setPlayers(new ArrayList<>());

        player = new Player();
        player.setId(10L);
        player.setFullName("Messi");
        player.setAge(35);
        player.setExperienceMonth(200);
        player.setTeam(fromTeam);

        transfer = new Transfer();
        transfer.setId(100L);
        transfer.setPlayer(player);
        transfer.setFromTeam(fromTeam);
        transfer.setToTeam(toTeam);
        transfer.setTransferPrice(1000000.0);
        transfer.setCommission(0.1);
        transfer.setTotalPrice(1100000.0);

        transferDto = new TransferDto();
        transferDto.setPlayerId(10L);
        transferDto.setToTeamId(2L);

        transferResponseDto = new TransferResponseDto();
        transferResponseDto.setId(100L);
        transferResponseDto.setPlayerName("Messi");
        transferResponseDto.setFromTeamName("Barcelona");
        transferResponseDto.setToTeamName("PSG");
        transferResponseDto.setTotalPrice(1100000.0);
    }

    @Test
    void testCreateTransfer_Success() {
        when(playerRepository.findById(10L)).thenReturn(Optional.of(player));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(toTeam));
        when(transferRepository.save(any(Transfer.class))).thenReturn(transfer);
        when(transferMapper.toDto(transfer)).thenReturn(transferResponseDto);

        TransferResponseDto result = transferService.createTransfer(transferDto);

        assertNotNull(result);
        assertEquals("Messi", result.getPlayerName());
        verify(playerRepository).findById(10L);
        verify(teamRepository).findById(2L);
        verify(transferRepository).save(any(Transfer.class));
        verify(transferMapper).toDto(transfer);
    }

    @Test
    void testCreateTransfer_PlayerHasNoTeam_ThrowsException() {
        player.setTeam(null); // Без команди
        when(playerRepository.findById(10L)).thenReturn(Optional.of(player));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(toTeam));

        assertThrows(IllegalArgumentException.class,
                () -> transferService.createTransfer(transferDto));

        verify(transferRepository, never()).save(any());
    }

    @Test
    void testCreateTransfer_TeamAlreadyHasPlayer_ThrowsException() {
        toTeam.getPlayers().add(player); // Гравець уже в команді
        when(playerRepository.findById(10L)).thenReturn(Optional.of(player));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(toTeam));

        assertThrows(IllegalArgumentException.class,
                () -> transferService.createTransfer(transferDto));

        verify(transferRepository, never()).save(any());
    }

    @Test
    void testFindById_Exists() {
        when(transferRepository.existsById(100L)).thenReturn(true);
        when(transferRepository.findById(100L)).thenReturn(Optional.of(transfer));
        when(transferMapper.toDto(transfer)).thenReturn(transferResponseDto);

        TransferResponseDto result = transferService.findById(100L);

        assertNotNull(result);
        assertEquals("Messi", result.getPlayerName());
        verify(transferRepository).existsById(100L);
        verify(transferRepository).findById(100L);
        verify(transferMapper).toDto(transfer);
    }

    @Test
    void testFindById_NotExists_ThrowsException() {
        when(transferRepository.existsById(100L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> transferService.findById(100L));

        verify(transferRepository).existsById(100L);
        verify(transferRepository, never()).findById(100L);
    }

    @Test
    void testFindAll_ReturnsList() {
        when(transferRepository.findAll()).thenReturn(List.of(transfer));
        when(transferMapper.toDto(transfer)).thenReturn(transferResponseDto);

        List<TransferResponseDto> result = transferService.findAll();

        assertEquals(1, result.size());
        assertEquals("Messi", result.get(0).getPlayerName());
        verify(transferRepository).findAll();
        verify(transferMapper).toDto(transfer);
    }

    @Test
    void testFindAll_EmptyList() {
        when(transferRepository.findAll()).thenReturn(List.of());

        List<TransferResponseDto> result = transferService.findAll();

        assertTrue(result.isEmpty());
        verify(transferRepository).findAll();
        verify(transferMapper, never()).toDto(any());
    }

    @Test
    void testDeleteById_Exists() {
        when(transferRepository.existsById(100L)).thenReturn(true);

        transferService.deleteById(100L);

        verify(transferRepository).existsById(100L);
        verify(transferRepository).deleteById(100L);
    }

    @Test
    void testDeleteById_NotExists_ThrowsException() {
        when(transferRepository.existsById(100L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> transferService.deleteById(100L));

        verify(transferRepository).existsById(100L);
        verify(transferRepository, never()).deleteById(any());
    }
}
