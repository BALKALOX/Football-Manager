package com.example.foorball_manager.service;

import com.example.foorball_manager.dto.TransferDto;
import com.example.foorball_manager.dto.TransferResponseDto;
import com.example.foorball_manager.entity.Transfer;

import java.util.List;

public interface TransferService {
    TransferResponseDto createTransfer(TransferDto transferDto);

    TransferResponseDto findById(Long id);

    List<TransferResponseDto> findAll();

    void deleteById(Long id);
}
