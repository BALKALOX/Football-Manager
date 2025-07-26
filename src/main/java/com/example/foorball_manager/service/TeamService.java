package com.example.foorball_manager.service;

import com.example.foorball_manager.dto.TeamDto;
import com.example.foorball_manager.entity.Team;

import java.util.List;

public interface TeamService {
    List<TeamDto> findAll();

    TeamDto findById(Long id);

    TeamDto createTeam(TeamDto teamDto);

    TeamDto updateTeam(Long id, TeamDto teamDto);

    void deleteTeam(Long id);
}
