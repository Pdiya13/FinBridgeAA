package com.FinBridgeAA.consent_service.DTO;

import lombok.Data;

@Data
public class ConsentValidateRequestDTO {
    private String consentId;
    private String purpose;
}