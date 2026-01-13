package com.FinBridgeAA.Auth_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInterospectResponse {
    private boolean active;
    private String userId;
    private String role;
    private String reason;
}
