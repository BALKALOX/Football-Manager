package com.example.foorball_manager.repository;

import com.example.foorball_manager.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsById(Long id);
    List<Player> findAll();
    Optional<Player> findById(Long id);
    Optional<Player> findByFullName(String fullName);
    Player save(Player player);
    void deleteById(Long id);
}
