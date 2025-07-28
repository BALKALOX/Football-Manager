package com.example.foorball_manager.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    @NotBlank(message = "full name is required")
    private String fullName;

    @NotNull
    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @Null
    @Min(value = 0, message = "Experience must be non-negative")
    private int experienceMonth;

    @Null
    private Long teamId;

}
