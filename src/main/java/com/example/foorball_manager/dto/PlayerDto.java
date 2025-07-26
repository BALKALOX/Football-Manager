package com.example.foorball_manager.dto;

import jakarta.validation.constraints.Null;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private String fullName;
    private int age;
    private int experienceMonth;

    @Null
    private Long teamId;

}
