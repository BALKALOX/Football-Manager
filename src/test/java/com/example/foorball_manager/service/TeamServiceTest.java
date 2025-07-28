package com.example.foorball_manager.service;

import com.example.foorball_manager.dto.TeamDto;
import com.example.foorball_manager.entity.Team;
import com.example.foorball_manager.exeption.ResourceNotFoundException;
import com.example.foorball_manager.mapper.TeamMapper;
import com.example.foorball_manager.repository.TeamRepository;
import com.example.foorball_manager.repository.TransferRepository;
import com.example.foorball_manager.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    private Team team;
    private TeamDto teamDto;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Barcelona");
        team.setBalance(1000000.0);
        team.setCommission(0.1);

        teamDto = new TeamDto();
        teamDto.setName("Barcelona");
        teamDto.setBalance(1000000.0);
        teamDto.setCommission(0.1);
    }

    @Test
    void testFindAll_ReturnsList() {
        when(teamRepository.findAll()).thenReturn(Arrays.asList(team));
        when(teamMapper.toDto(team)).thenReturn(teamDto);

        List<TeamDto> result = teamService.findAll();

        assertEquals(1, result.size());
        assertEquals("Barcelona", result.get(0).getName());
        verify(teamRepository).findAll();
        verify(teamMapper).toDto(team);
    }

    @Test
    void testFindAll_EmptyList() {
        when(teamRepository.findAll()).thenReturn(List.of());

        List<TeamDto> result = teamService.findAll();

        assertTrue(result.isEmpty());
        verify(teamRepository).findAll();
        verify(teamMapper, never()).toDto(any());
    }

    @Test
    void testFindById_TeamExists() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamMapper.toDto(team)).thenReturn(teamDto);

        TeamDto result = teamService.findById(1L);

        assertNotNull(result);
        assertEquals("Barcelona", result.getName());
        verify(teamRepository).findById(1L);
        verify(teamMapper).toDto(team);
    }

    @Test
    void testFindById_TeamNotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> teamService.findById(1L));
        verify(teamRepository).findById(1L);
        verify(teamMapper, never()).toDto(any());
    }

    @Test
    void testCreateTeam_Success() {
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        when(teamMapper.toDto(team)).thenReturn(teamDto);

        TeamDto result = teamService.createTeam(teamDto);

        assertNotNull(result);
        assertEquals("Barcelona", result.getName());
        verify(teamRepository).save(any(Team.class));
        verify(teamMapper).toDto(team);
    }

    @Test
    void testUpdateTeam_TeamExists() {
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(team);
        when(teamMapper.toDto(team)).thenReturn(teamDto);

        TeamDto result = teamService.updateTeam(1L, teamDto);

        assertNotNull(result);
        assertEquals("Barcelona", result.getName());
        verify(teamRepository).save(any(Team.class));
        verify(teamMapper).toDto(team);
    }
    @Test
    void testUpdateTeam_TeamNotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> teamService.updateTeam(1L, teamDto));

        assertEquals("Team with id:1 is not found", exception.getMessage());

        verify(teamRepository).findById(1L);
        verify(teamRepository, never()).save(any());
        verify(teamMapper, never()).toDto(any());
    }

    @Test
    void testDeleteTeam_TeamExists() {
        Team team = new Team();
        team.setId(1L);
        team.setPlayers(new ArrayList<>());

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(transferRepository.findAllByFromTeam(team)).thenReturn(new ArrayList<>());
        when(transferRepository.findAllByToTeam(team)).thenReturn(new ArrayList<>());

        teamService.deleteTeam(1L);

        verify(teamRepository).findById(1L);
        verify(teamRepository).delete(team);
    }

    @Test
    void testDeleteTeam_TeamNotFound() {
        when(teamRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> teamService.deleteTeam(1L));

        assertEquals("Team with id:1 is not found", exception.getMessage());

        verify(teamRepository).findById(1L);
        verify(teamRepository, never()).delete(any());
    }
}