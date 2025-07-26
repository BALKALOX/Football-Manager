package com.example.foorball_manager.service;

import com.example.foorball_manager.dto.PlayerDto;
import com.example.foorball_manager.entity.Player;
import com.example.foorball_manager.entity.Team;
import com.example.foorball_manager.mapper.PlayerMapper;
import com.example.foorball_manager.repository.PlayerRepository;
import com.example.foorball_manager.repository.TeamRepository;
import com.example.foorball_manager.service.impl.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;
    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void testFindById_PlayerExists() {
        Player player = new Player();
        player.setId(1L);
        player.setFullName("Messi");

        PlayerDto playerDto = new PlayerDto();
        playerDto.setFullName("Messi");
        playerDto.setTeamId(1L);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.existsById(1L)).thenReturn(true);
        when(playerMapper.toDto(player)).thenReturn(playerDto);

        PlayerDto result = playerService.findById(1L);

        assertNotNull(result);
        assertEquals("Messi", result.getFullName());
        verify(playerRepository).findById(1L);
        verify(playerRepository).existsById(any());
        verify(playerMapper).toDto(player);
    }

    @Test
    void testFindById_PlayerDoesNotExist() {
        when(playerRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> playerService.findById(1L));

        verify(playerRepository, never()).findById(1L);
    }

    @Test
    void testCreatePlayer_Success() {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFullName("Messi");
        playerDto.setTeamId(1L);

        Team team = new Team();
        team.setId(1L);
        team.setName("Barcelona");

        Player player = new Player();
        player.setId(1L);
        player.setFullName("Messi");
        player.setTeam(team);

        when(playerMapper.toEntity(playerDto)).thenReturn(player);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(playerRepository.save(player)).thenReturn(player);
        when(playerMapper.toDto(player)).thenReturn(playerDto);

        PlayerDto result = playerService.createPlayer(playerDto);

        assertNotNull(result);
        assertEquals("Messi", result.getFullName());
        assertEquals(1L, result.getTeamId());

        verify(playerMapper).toEntity(playerDto);
        verify(teamRepository).findById(1L);
        verify(playerRepository).save(player);
        verify(playerMapper).toDto(player);
    }

    @Test
    void testCreatePlayer_TeamNotFound() {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFullName("Messi");
        playerDto.setTeamId(99L);

        Player player = new Player();
        player.setFullName("Messi");

        when(playerMapper.toEntity(playerDto)).thenReturn(player);
        when(teamRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> playerService.createPlayer(playerDto));

        verify(teamRepository).findById(99L);
        verify(playerRepository, never()).save(any());
    }

    @Test
    void testUpdatePlayer_Success() {
        Long playerId = 1L;

        PlayerDto updateDto = new PlayerDto();
        updateDto.setFullName("Updated Messi");
        updateDto.setTeamId(2L);

        Team newTeam = new Team();
        newTeam.setId(2L);
        newTeam.setName("PSG");

        Player mappedPlayer = new Player();
        mappedPlayer.setFullName("Updated Messi");
        mappedPlayer.setTeam(newTeam);

        PlayerDto expectedDto = new PlayerDto();
        expectedDto.setFullName("Updated Messi");
        expectedDto.setTeamId(2L);

        when(playerRepository.existsById(playerId)).thenReturn(true);
        when(playerMapper.toEntity(updateDto)).thenReturn(mappedPlayer);
        when(playerMapper.toDto(mappedPlayer)).thenReturn(expectedDto);

        PlayerDto result = playerService.updatePlayer(playerId, updateDto);

        assertNotNull(result);
        assertEquals("Updated Messi", result.getFullName());
        assertEquals(2L, result.getTeamId());

        verify(playerRepository).existsById(playerId);
        verify(playerMapper).toEntity(updateDto);
        verify(playerMapper).toDto(mappedPlayer);
    }

    @Test
    void testUpdatePlayer_PlayerNotFound() {
        Long playerId = 99L;

        PlayerDto updateDto = new PlayerDto();
        updateDto.setFullName("Messi");
        updateDto.setTeamId(1L);

        when(playerRepository.existsById(playerId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> playerService.updatePlayer(playerId, updateDto));

        assertEquals("player with id:99 is not found", exception.getMessage());

        verify(playerRepository).existsById(playerId);
        verify(playerMapper, never()).toEntity(any());
        verify(teamRepository, never()).findById(any());
    }

    @Test
    void testUpdatePlayer_TeamIdChanged() {
        Long playerId = 1L;

        PlayerDto updateDto = new PlayerDto();
        updateDto.setFullName("Messi");
        updateDto.setTeamId(2L);

        Team newTeam = new Team();
        newTeam.setId(2L);
        newTeam.setName("PSG");

        Player mappedPlayer = new Player();
        mappedPlayer.setFullName("Messi");
        mappedPlayer.setTeam(new Team());

        PlayerDto expectedDto = new PlayerDto();
        expectedDto.setFullName("Messi");
        expectedDto.setTeamId(2L);

        when(playerRepository.existsById(playerId)).thenReturn(true);
        when(playerMapper.toEntity(updateDto)).thenReturn(mappedPlayer);
        when(teamRepository.findById(2L)).thenReturn(Optional.of(newTeam));
        when(playerMapper.toDto(mappedPlayer)).thenReturn(expectedDto);

        PlayerDto result = playerService.updatePlayer(playerId, updateDto);

        assertNotNull(result);
        assertEquals(2L, result.getTeamId());

        verify(playerRepository).existsById(playerId);
        verify(teamRepository).findById(2L);
    }

    @Test
    void testDeletePlayer_Success() {
        Long playerId = 1L;

        when(playerRepository.existsById(playerId)).thenReturn(true);

        playerService.deletePlayer(playerId);

        verify(playerRepository).existsById(playerId);
        verify(playerRepository).deleteById(playerId);
    }

    @Test
    void testDeletePlayer_PlayerNotFound() {
        Long playerId = 99L;

        when(playerRepository.existsById(playerId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> playerService.deletePlayer(playerId));

        assertEquals("player with id:99 is not found", exception.getMessage());

        verify(playerRepository).existsById(playerId);
        verify(playerRepository, never()).deleteById(any());
    }
}
