package com.example.foorball_manager.controller;

import com.example.foorball_manager.dto.TeamDto;
import com.example.foorball_manager.entity.Team;
import com.example.foorball_manager.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teams")
@AllArgsConstructor
public class TeamController {

    private TeamService teamService;


    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        var teams = teamService.findAll();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        var team = teamService.findById(id);
        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto) {
        var team = teamService.createTeam(teamDto);
        return ResponseEntity.ok(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id,
                                           @RequestBody TeamDto teamDto) {
        var team = teamService.updateTeam(id, teamDto);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
