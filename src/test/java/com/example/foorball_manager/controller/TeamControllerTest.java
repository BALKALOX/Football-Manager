package com.example.foorball_manager.controller;

import com.example.foorball_manager.dto.TeamDto;
import com.example.foorball_manager.service.TeamService;
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

@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeamService teamService;

    @Test
    void getAllTeams_shouldReturnList() throws Exception {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Barcelona");
        teamDto.setBalance(1000000.0);
        teamDto.setCommission(0.1);

        when(teamService.findAll()).thenReturn(List.of(teamDto));

        mockMvc.perform(get("/api/v1/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Barcelona"))
                .andExpect(jsonPath("$[0].balance").value(1000000.0))
                .andExpect(jsonPath("$[0].commission").value(0.1));
    }

    @Test
    void getTeamById_shouldReturnTeam() throws Exception {
        TeamDto teamDto = new TeamDto();
        teamDto.setName("Barcelona");
        teamDto.setBalance(1000000.0);
        teamDto.setCommission(0.1);

        when(teamService.findById(1L)).thenReturn(teamDto);

        mockMvc.perform(get("/api/v1/teams/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Barcelona"))
                .andExpect(jsonPath("$.balance").value(1000000.0))
                .andExpect(jsonPath("$.commission").value(0.1));
    }

    @Test
    void createTeam_shouldReturnCreatedTeam() throws Exception {
        TeamDto requestDto = new TeamDto();
        requestDto.setName("Barcelona");
        requestDto.setBalance(1000000.0);
        requestDto.setCommission(0.1);

        TeamDto responseDto = new TeamDto();
        responseDto.setName("Barcelona");
        responseDto.setBalance(1000000.0);
        responseDto.setCommission(0.1);

        when(teamService.createTeam(any(TeamDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Barcelona"))
                .andExpect(jsonPath("$.balance").value(1000000.0))
                .andExpect(jsonPath("$.commission").value(0.1));
    }

    @Test
    void updateTeam_shouldReturnUpdatedTeam() throws Exception {
        TeamDto requestDto = new TeamDto();
        requestDto.setName("Real Madrid");
        requestDto.setBalance(2000000.0);
        requestDto.setCommission(0.2);

        TeamDto responseDto = new TeamDto();
        responseDto.setName("Real Madrid");
        responseDto.setBalance(2000000.0);
        responseDto.setCommission(0.2);

        when(teamService.updateTeam(eq(1L), any(TeamDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/teams/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Real Madrid"))
                .andExpect(jsonPath("$.balance").value(2000000.0))
                .andExpect(jsonPath("$.commission").value(0.2));
    }

    @Test
    void deleteTeam_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/v1/teams/1"))
                .andExpect(status().isOk());
    }
}
