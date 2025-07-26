package com.example.foorball_manager.service.impl;

import com.example.foorball_manager.dto.TeamDto;
import com.example.foorball_manager.entity.Player;
import com.example.foorball_manager.entity.Team;
import com.example.foorball_manager.entity.Transfer;
import com.example.foorball_manager.mapper.TeamMapper;
import com.example.foorball_manager.repository.PlayerRepository;
import com.example.foorball_manager.repository.TeamRepository;
import com.example.foorball_manager.repository.TransferRepository;
import com.example.foorball_manager.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private TeamMapper teamMapper;
    private TransferRepository transferRepository;
    private PlayerRepository playerRepository;

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
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team with id:" + id + " is not found"));

        for (Player player : team.getPlayers()) {
            player.setTeam(null);
            playerRepository.save(player);
        }

        List<Transfer> fromTransfers = transferRepository.findAllByFromTeam(team);
        for (Transfer transfer : fromTransfers) {
            System.out.println("FromTransfer id=" + transfer.getId() + ", player id=" +
                    (transfer.getPlayer() != null ? transfer.getPlayer().getId() : "null"));
        }

        List<Transfer> toTransfers = transferRepository.findAllByToTeam(team);
        for (Transfer transfer : toTransfers) {
            System.out.println("ToTransfer id=" + transfer.getId() + ", player id=" +
                    (transfer.getPlayer() != null ? transfer.getPlayer().getId() : "null"));
        }

        transferRepository.saveAll(fromTransfers);
        transferRepository.saveAll(toTransfers);

        teamRepository.delete(team);
    }

}
