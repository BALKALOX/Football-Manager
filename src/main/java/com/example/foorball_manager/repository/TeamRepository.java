package com.example.foorball_manager.repository;

import com.example.foorball_manager.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findAll();
    Optional<Team> findById(Long id);
    Optional<Team> findByName(String name);
    Team save(Team team);
    void deleteById(Long id);
}
