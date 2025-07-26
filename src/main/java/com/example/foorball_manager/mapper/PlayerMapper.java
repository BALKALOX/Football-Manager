package com.example.foorball_manager.mapper;

import com.example.foorball_manager.dto.PlayerDto;
import com.example.foorball_manager.entity.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDto toDto (Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFullName(player.getFullName());
        playerDto.setAge(player.getAge());
        playerDto.setExperienceMonth(player.getExperienceMonth());
        playerDto.setTeamId(player.getTeam().getId());

        return playerDto;
    }

    public Player toEntity (PlayerDto playerDto) {
        Player player = new Player();
        player.setFullName(playerDto.getFullName());
        player.setAge(playerDto.getAge());
        player.setExperienceMonth(playerDto.getExperienceMonth());

        return player;
    }
}
