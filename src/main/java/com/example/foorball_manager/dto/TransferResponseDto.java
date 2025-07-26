package com.example.foorball_manager.dto;

import lombok.Data;

@Data
public class TransferResponseDto {
    private Long id;
    private Long playerId;
    private String playerName;
    private String fromTeamName;
    private String toTeamName;
    private double transferPrice;
    private double commission;
    private double totalPrice;
}
