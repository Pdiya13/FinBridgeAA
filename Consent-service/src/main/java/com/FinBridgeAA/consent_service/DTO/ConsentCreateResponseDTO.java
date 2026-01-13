package com.FinBridgeAA.consent_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ConsentCreateResponseDTO {
    private UUID consentId;
    private String status;
}