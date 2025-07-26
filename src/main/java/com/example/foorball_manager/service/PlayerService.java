package com.example.foorball_manager.service;

import com.example.foorball_manager.dto.PlayerDto;
import com.example.foorball_manager.entity.Player;

import java.util.List;

public interface PlayerService {
    List<PlayerDto> findAll();

    PlayerDto findById(Long id);

    PlayerDto createPlayer(PlayerDto playerDto);

    PlayerDto updatePlayer(Long id, PlayerDto playerDto);

    void deletePlayer(Long id);
}
