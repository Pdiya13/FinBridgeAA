package com.FinBridgeAA.Auth_service.DTO;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    public String phoneNumber;
    public String otp;
}
