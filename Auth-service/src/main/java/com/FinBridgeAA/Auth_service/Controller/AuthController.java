package com.FinBridgeAA.Auth_service.Controller;

import com.FinBridgeAA.Auth_service.DTO.OtpRequest;
import com.FinBridgeAA.Auth_service.DTO.VerifyOtpRequest;
import com.FinBridgeAA.Auth_service.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOTP(@RequestBody OtpRequest request) {
        authService.requestOtp(request.getPhoneNumber());
        return ResponseEntity.ok("OTP sent");
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody VerifyOtpRequest request) {
        authService.VerifyOtp(request.getPhoneNumber(), request.getOtp());
        return ResponseEntity.ok("OTP Verified");
    }
}
