package com.example.foorball_manager.repository;

import com.example.foorball_manager.entity.Player;
import com.example.foorball_manager.entity.Team;
import com.example.foorball_manager.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAll();
    List<Transfer> findAllByFromTeam(Team from);
    List<Transfer> findAllByToTeam(Team to);
    Optional<Transfer> findById(Long id);
    List<Transfer> findByPlayerId(Long userId);
    Transfer save(Transfer transfer);
    void deleteById(Long id);
    void deleteAllByPlayer(Player player);
}
