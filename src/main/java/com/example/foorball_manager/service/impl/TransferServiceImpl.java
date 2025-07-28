package com.example.foorball_manager.service.impl;

import com.example.foorball_manager.dto.TransferDto;
import com.example.foorball_manager.dto.TransferResponseDto;
import com.example.foorball_manager.entity.Transfer;
import com.example.foorball_manager.exeption.BadRequestException;
import com.example.foorball_manager.exeption.ResourceNotFoundException;
import com.example.foorball_manager.mapper.TransferMapper;
import com.example.foorball_manager.repository.PlayerRepository;
import com.example.foorball_manager.repository.TeamRepository;
import com.example.foorball_manager.repository.TransferRepository;
import com.example.foorball_manager.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TransferServiceImpl implements TransferService {

    private TransferRepository transferRepository;
    private PlayerRepository playerRepository;
    private TeamRepository teamRepository;
    private TransferMapper transferMapper;

    @Override
    public TransferResponseDto createTransfer(TransferDto transferDto) {
        var player = playerRepository.findById(transferDto.getPlayerId()).get();
        var team = teamRepository.findById(transferDto.getToTeamId()).get();
        if (player == null || team == null) {
            throw new IllegalArgumentException();
        }
        if (player.getTeam()==null){
            throw new BadRequestException("Player has no team");
        }
        if (team.getPlayers().contains(player)){
            throw new BadRequestException("Team already has player");
        }
        Transfer transfer = new Transfer();
        transfer.setPlayer(player);
        transfer.setFromTeam(player.getTeam());
        transfer.setToTeam(team);
        transfer.setTransferPrice(player.getExperienceMonth()*100000.0/player.getAge());
        transfer.setCommission(team.getCommission());
        transfer.setTotalPrice(
                transfer.getTransferPrice()+transfer.getTransferPrice()*transfer.getCommission()
        );
        transfer.setTransferDate(LocalDateTime.now());
        return transferMapper.toDto(transferRepository.save(transfer));
    }

    @Override
    public TransferResponseDto findById(Long id) {
        if (!transferRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transfer with id " + id + " is not found");
        }
        return transferMapper.toDto(transferRepository.findById(id).get());
    }

    @Override
    public List<TransferResponseDto> findAll() {
        var transfers = transferRepository.findAll();
        var dtos = new ArrayList<TransferResponseDto>();
        for (var transfer : transfers) {
            dtos.add(transferMapper.toDto(transfer));
        }
        return dtos;
    }

    @Override
    public void deleteById(Long id) {
        if (!transferRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transfer with id " + id + " is not found");
        }
        transferRepository.deleteById(id);
    }
}
