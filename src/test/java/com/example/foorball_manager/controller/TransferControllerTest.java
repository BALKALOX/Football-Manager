package com.example.foorball_manager.controller;

import com.example.foorball_manager.dto.TransferDto;
import com.example.foorball_manager.dto.TransferResponseDto;
import com.example.foorball_manager.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransferController.class)
class TransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransferService transferService;

    @Test
    void getAllTransfers_shouldReturnList() throws Exception {
        TransferResponseDto transfer = new TransferResponseDto();
        transfer.setId(1L);
        transfer.setTransferPrice(500000.0);
        transfer.setCommission(0.1);
        transfer.setTotalPrice(550000.0);

        when(transferService.findAll()).thenReturn(List.of(transfer));

        mockMvc.perform(get("/api/v1/transfers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].transferPrice").value(500000.0))
                .andExpect(jsonPath("$[0].commission").value(0.1))
                .andExpect(jsonPath("$[0].totalPrice").value(550000.0));
    }

    @Test
    void getTransferById_shouldReturnTransfer() throws Exception {
        TransferResponseDto transfer = new TransferResponseDto();
        transfer.setId(1L);
        transfer.setTransferPrice(500000.0);
        transfer.setCommission(0.1);
        transfer.setTotalPrice(550000.0);

        when(transferService.findById(1L)).thenReturn(transfer);

        mockMvc.perform(get("/api/v1/transfers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.transferPrice").value(500000.0))
                .andExpect(jsonPath("$.commission").value(0.1))
                .andExpect(jsonPath("$.totalPrice").value(550000.0));
    }

    @Test
    void createTransfer_shouldReturnCreatedTransfer() throws Exception {
        TransferDto requestDto = new TransferDto();
        requestDto.setPlayerId(10L);
        requestDto.setToTeamId(20L);

        TransferResponseDto responseDto = new TransferResponseDto();
        responseDto.setId(1L);
        responseDto.setTransferPrice(500000.0);
        responseDto.setCommission(0.1);
        responseDto.setTotalPrice(550000.0);

        when(transferService.createTransfer(any(TransferDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.transferPrice").value(500000.0))
                .andExpect(jsonPath("$.commission").value(0.1))
                .andExpect(jsonPath("$.totalPrice").value(550000.0));
    }
}
