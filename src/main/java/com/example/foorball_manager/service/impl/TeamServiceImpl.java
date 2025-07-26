package com.example.foorball_manager.service.impl;

import com.example.foorball_manager.dto.TeamDto;
import com.example.foorball_manager.entity.Team;
import com.example.foorball_manager.mapper.TeamMapper;
import com.example.foorball_manager.repository.TeamRepository;
import com.example.foorball_manager.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private TeamMapper teamMapper;

    @Override
    public List<TeamDto> findAll() {
        var teams = teamRepository.findAll();
        List<TeamDto> teamDtos = new ArrayList<>();
        for (var team : teams) {
            teamDtos.add(teamMapper.toDto(team));
        }
        return teamDtos;
    }

    @Override
    public TeamDto findById(Long id) {
        return teamMapper.toDto(teamRepository.findById(id).get());
    }

    @Override
    public TeamDto createTeam(TeamDto teamDto) {
        var team = new Team();
        team.setName(teamDto.getName());
        team.setBalance(teamDto.getBalance());
        team.setCommission(teamDto.getCommission());
        return teamMapper.toDto(teamRepository.save(team));
    }

    @Override
    public TeamDto updateTeam(Long id, TeamDto teamDto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team with id:" + id + " is not found"));

        team.setName(teamDto.getName());
        team.setBalance(teamDto.getBalance());
        team.setCommission(teamDto.getCommission());

        return teamMapper.toDto(teamRepository.save(team));
    }

    @Override
    public void deleteTeam(Long id) {
        var exists = teamRepository.existsById(id);
        if (!exists){
            throw new IllegalArgumentException("Team with id:" + id +" is not found");
        }
        teamRepository.deleteById(id);
    }

}
