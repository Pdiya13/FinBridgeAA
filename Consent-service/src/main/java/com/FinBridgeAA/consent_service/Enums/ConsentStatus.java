package com.FinBridgeAA.consent_service.Enums;

import lombok.Data;

@Data
public enum ConsentStatus {
    PENDING,
    APPROVED,
    REJECTED,
    REVOKED,
    EXPIRED
}