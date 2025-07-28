package com.example.foorball_manager.controller;

import com.example.foorball_manager.dto.PlayerDto;
import com.example.foorball_manager.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlayerService playerService;

    @Test
    void getAllPlayers_shouldReturnList() throws Exception {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFullName("Messi");
        playerDto.setTeamId(1L);

        when(playerService.findAll()).thenReturn(List.of(playerDto));

        mockMvc.perform(get("/api/v1/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Messi"))
                .andExpect(jsonPath("$[0].teamId").value(1));
    }

    @Test
    void getPlayerById_shouldReturnPlayer() throws Exception {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setFullName("Messi");
        playerDto.setTeamId(1L);

        when(playerService.findById(1L)).thenReturn(playerDto);

        mockMvc.perform(get("/api/v1/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Messi"))
                .andExpect(jsonPath("$.teamId").value(1));
    }

    @Test
    void createPlayer_shouldReturnCreatedPlayer() throws Exception {
        PlayerDto requestDto = new PlayerDto();
        requestDto.setFullName("Messi");
        requestDto.setTeamId(1L);

        PlayerDto responseDto = new PlayerDto();
        responseDto.setFullName("Messi");
        responseDto.setTeamId(1L);

        when(playerService.createPlayer(any(PlayerDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Messi"))
                .andExpect(jsonPath("$.teamId").value(1));
    }

    @Test
    void updatePlayer_shouldReturnUpdatedPlayer() throws Exception {
        PlayerDto requestDto = new PlayerDto();
        requestDto.setFullName("Messi Updated");
        requestDto.setTeamId(2L);

        PlayerDto responseDto = new PlayerDto();
        responseDto.setFullName("Messi Updated");
        responseDto.setTeamId(2L);

        when(playerService.updatePlayer(eq(1L), any(PlayerDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Messi Updated"))
                .andExpect(jsonPath("$.teamId").value(2));
    }

    @Test
    void deletePlayer_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/players/1"))
                .andExpect(status().isNoContent());
    }
}
