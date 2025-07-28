package com.example.foorball_manager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamDto {
    @NotBlank
    private String name;
    @NotNull
    @Min(0)
    private double balance;
    @NotNull
    @Min(0)
    private double commission;
    @Null
    private List<PlayerDto> players;
}
