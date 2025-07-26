package com.example.foorball_manager.mapper;

import com.example.foorball_manager.dto.TransferResponseDto;
import com.example.foorball_manager.entity.Transfer;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {

        public TransferResponseDto toDto(Transfer transfer) {
            if (transfer == null) return null;

            TransferResponseDto dto = new TransferResponseDto();
            dto.setId(transfer.getId());
            dto.setPlayerId(transfer.getPlayer().getId());
            dto.setPlayerName(transfer.getPlayer().getFullName());
            dto.setFromTeamName(transfer.getFromTeam().getName());
            dto.setToTeamName(transfer.getToTeam().getName());
            dto.setTransferPrice(transfer.getTransferPrice());
            dto.setCommission(transfer.getCommission());
            dto.setTotalPrice(transfer.getTotalPrice());

            return dto;
        }
}
