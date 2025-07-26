package com.example.foorball_manager.mapper;

import com.example.foorball_manager.dto.TeamDto;
import com.example.foorball_manager.entity.Team;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TeamMapper {

    private final PlayerMapper playerMapper;

    public TeamMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    public TeamDto toDto(Team team) {
        if (team == null) return null;

        TeamDto dto = new TeamDto();
        dto.setName(team.getName());
        dto.setBalance(team.getBalance());
        dto.setCommission(team.getCommission());

        if (team.getPlayers() != null) {
            dto.setPlayers(team.getPlayers().stream()
                    .map(playerMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Team toEntity(TeamDto dto) {
        if (dto == null) return null;

        Team team = new Team();
        team.setName(dto.getName());
        team.setBalance(dto.getBalance());
        team.setCommission(dto.getCommission());

        return team;
    }
}
