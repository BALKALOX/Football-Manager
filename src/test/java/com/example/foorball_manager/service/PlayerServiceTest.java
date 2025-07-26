package com.example.foorball_manager.service;

import com.example.foorball_manager.dto.PlayerDto;
import com.example.foorball_manager.entity.Player;
import com.example.foorball_manager.entity.Team;
import com.example.foorball_manager.mapper.PlayerMapper;
import com.example.foorball_manager.mapper.TeamMapper;
import com.example.foorball_manager.repository.PlayerRepository;
import com.example.foorball_manager.repository.TeamRepository;
import com.example.foorball_manager.repository.TransferRepository;
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

    @Mock
    private TransferRepository transferRepository;
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
        updateDto.setExperienceMonth(120);
        updateDto.setAge(35);
        updateDto.setTeamId(2L);

        Player existingPlayer = new Player();
        existingPlayer.setId(playerId);
        existingPlayer.setFullName("Old Messi");
        existingPlayer.setExperienceMonth(100);
        existingPlayer.setAge(34);

        Team newTeam = new Team();
        newTeam.setId(2L);
        newTeam.setName("PSG");

        Player updatedPlayer = new Player();
        updatedPlayer.setId(playerId);
        updatedPlayer.setFullName("Updated Messi");
        updatedPlayer.setExperienceMonth(120);
        updatedPlayer.setAge(35);
        updatedPlayer.setTeam(newTeam);

        PlayerDto expectedDto = new PlayerDto();
        expectedDto.setFullName("Updated Messi");
        expectedDto.setExperienceMonth(120);
        expectedDto.setAge(35);
        expectedDto.setTeamId(2L);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(existingPlayer));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(newTeam));
        when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);
        when(playerMapper.toDto(updatedPlayer)).thenReturn(expectedDto);

        PlayerDto result = playerService.updatePlayer(playerId, updateDto);

        assertNotNull(result);
        assertEquals("Updated Messi", result.getFullName());
        assertEquals(120, result.getExperienceMonth());
        assertEquals(35, result.getAge());
        assertEquals(2L, result.getTeamId());

        verify(playerRepository).findById(playerId);
        verify(teamRepository).findById(2L);
        verify(playerRepository).save(any(Player.class));
        verify(playerMapper).toDto(any(Player.class));
    }

    @Test
    void testUpdatePlayer_PlayerNotFound() {
        Long playerId = 99L;

        PlayerDto updateDto = new PlayerDto();
        updateDto.setFullName("Messi");
        updateDto.setTeamId(1L);

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> playerService.updatePlayer(playerId, updateDto));

        assertEquals("Player with id:99 is not found", exception.getMessage());

        verify(teamRepository, never()).findById(any());
        verify(playerRepository, never()).save(any());
        verify(playerMapper, never()).toDto(any());
    }


    @Test
    void testUpdatePlayer_TeamIdChanged() {
        Long playerId = 1L;

        PlayerDto updateDto = new PlayerDto();
        updateDto.setFullName("Messi");
        updateDto.setTeamId(2L);

        Player existingPlayer = new Player();
        existingPlayer.setId(playerId);
        existingPlayer.setFullName("Old Messi");

        Team newTeam = new Team();
        newTeam.setId(2L);
        newTeam.setName("PSG");

        Player savedPlayer = new Player();
        savedPlayer.setId(playerId);
        savedPlayer.setFullName("Messi");
        savedPlayer.setTeam(newTeam);

        PlayerDto expectedDto = new PlayerDto();
        expectedDto.setFullName("Messi");
        expectedDto.setTeamId(2L);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(existingPlayer));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(newTeam));
        when(playerRepository.save(any(Player.class))).thenReturn(savedPlayer);
        when(playerMapper.toDto(savedPlayer)).thenReturn(expectedDto);

        PlayerDto result = playerService.updatePlayer(playerId, updateDto);

        assertNotNull(result);
        assertEquals("Messi", result.getFullName());
        assertEquals(2L, result.getTeamId());

        verify(playerRepository).findById(playerId);
        verify(teamRepository).findById(2L);
        verify(playerRepository).save(any(Player.class));
        verify(playerMapper).toDto(savedPlayer);
    }

    @Test
    void testDeletePlayer_Success() {
        Long playerId = 1L;

        Player existingPlayer = new Player();
        existingPlayer.setId(playerId);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(existingPlayer));

        playerService.deletePlayer(playerId);

        verify(playerRepository).findById(playerId);
        verify(transferRepository).deleteAllByPlayer(existingPlayer);
        verify(playerRepository).delete(existingPlayer);
    }


    @Test
    void testDeletePlayer_PlayerNotFound() {
        Long playerId = 99L;

        when(playerRepository.findById(playerId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> playerService.deletePlayer(playerId));

        assertEquals("player with id:99 is not found", exception.getMessage());

        verify(playerRepository).findById(playerId);

        verify(playerRepository, never()).delete(any());
        verify(transferRepository, never()).deleteAllByPlayer(any());
    }}
