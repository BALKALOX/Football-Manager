package com.example.foorball_manager.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamDto {
    private String name;
    private double balance;
    private double commission;
    private List<PlayerDto> players;
}
