package com.example.foorball_manager.controller;

import com.example.foorball_manager.dto.TransferDto;
import com.example.foorball_manager.dto.TransferResponseDto;
import com.example.foorball_manager.entity.Transfer;
import com.example.foorball_manager.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfers")
@AllArgsConstructor
public class TransferController {
    private TransferService transferService;

    @GetMapping
    public ResponseEntity<List<TransferResponseDto>> getAllTransfers() {
        return ResponseEntity.ok(transferService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponseDto> getTransferById(@PathVariable Long id) {
        return ResponseEntity.ok(transferService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TransferResponseDto> createTransfer(@RequestBody TransferDto transferDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transferService.createTransfer(transferDto));
    }

}
