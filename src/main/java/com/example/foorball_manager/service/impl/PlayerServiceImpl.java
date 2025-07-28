package com.example.foorball_manager.service.impl;

import com.example.foorball_manager.dto.PlayerDto;
import com.example.foorball_manager.entity.Player;
import com.example.foorball_manager.entity.Team;
import com.example.foorball_manager.exeption.ResourceNotFoundException;
import com.example.foorball_manager.mapper.PlayerMapper;
import com.example.foorball_manager.repository.PlayerRepository;
import com.example.foorball_manager.repository.TeamRepository;
import com.example.foorball_manager.repository.TransferRepository;
import com.example.foorball_manager.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;
    private PlayerMapper playerMapper;
    private TransferRepository transferRepository;

    @Override
    public List<PlayerDto> findAll() {
        var players = playerRepository.findAll();
        var dtos = new ArrayList<PlayerDto>();
        for (var player : players) {
            dtos.add(playerMapper.toDto(player));
        }
        return dtos;
    }

    @Override
    public PlayerDto findById(Long id) {
        var exists = playerRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("player with id:"+id+" is not found");
        }
        var player = playerRepository.findById(id).get();

        return playerMapper.toDto(player);
    }

    @Override
    public PlayerDto createPlayer(PlayerDto playerDto) {
        Player player = playerMapper.toEntity(playerDto);

        if (playerDto.getTeamId() != null) {
            Team team = teamRepository.findById(playerDto.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Team with id:" + playerDto.getTeamId() + " is not found"));

            player.setTeam(team);
        } else {
            player.setTeam(null);
        }

        return playerMapper.toDto(playerRepository.save(player));
    }


    @Override
    public PlayerDto updatePlayer(Long id, PlayerDto playerDto) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player with id:" + id + " is not found"));

        existingPlayer.setFullName(playerDto.getFullName());
        existingPlayer.setExperienceMonth(playerDto.getExperienceMonth());
        existingPlayer.setAge(playerDto.getAge());

        if (playerDto.getTeamId() != null) {
            Team newTeam = teamRepository.findById(playerDto.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team with id:" + playerDto.getTeamId() + " is not found"));

            existingPlayer.setTeam(newTeam);
        } else {
            existingPlayer.setTeam(null);
        }

        return playerMapper.toDto(playerRepository.save(existingPlayer));
    }

    @Override
    @Transactional
    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("player with id:" + id + " is not found"));

        transferRepository.deleteAllByPlayer(player);

        playerRepository.delete(player);
    }


}
