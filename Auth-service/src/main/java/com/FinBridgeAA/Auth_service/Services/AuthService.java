package com.FinBridgeAA.Auth_service.Services;

import com.FinBridgeAA.Auth_service.Entity.AuthUser;
import com.FinBridgeAA.Auth_service.Enums.Role;
import com.FinBridgeAA.Auth_service.Enums.Status;
import com.FinBridgeAA.Auth_service.Repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final OtpService otpService;
    private final JwtService jwtService;
    public AuthService(AuthUserRepository authUserRepository, OtpService otpService, JwtService jwtService) {
        this.authUserRepository = authUserRepository;
        this.otpService = otpService;
        this.jwtService = jwtService;
    }
    public void requestOtp(String phoneNumber) {
        String otp = otpService.generateOtp();
        System.out.println(otp);
        AuthUser user = authUserRepository
                .findByPhoneNumber(phoneNumber)
                .orElseGet(() -> createNewUser(phoneNumber));
        user.setOtpHash(otpService.encodeOtp(otp));
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        user.setOtpAttempts(0);
        authUserRepository.save(user);
    }
    private AuthUser createNewUser(String phoneNumber) {
        AuthUser user = new AuthUser();
        user.setPhoneNumber(phoneNumber);
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
    public String VerifyOtp(String phoneNumber, String otp) {
        AuthUser user = authUserRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Invalid request..."));

        if(user.getOtpExpiry() == null || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired...");
        }

        // attempt limit exceeded...
        if(user.getOtpAttempts() >= 5) {
            throw new RuntimeException("Too many otp attempts...");
        }

        if(!otpService.verifyOtp(otp, user.getOtpHash())) {
            user.setOtpAttempts(user.getOtpAttempts() + 1);
            authUserRepository.save(user);
            throw new RuntimeException("invalid OTP...");
        }


        user.setOtpHash(null);
        user.setOtpExpiry(null);
        user.setOtpAttempts(0);
        String token = jwtService.generateJWTToken(user);
        authUserRepository.save(user);
        return token;
    }
}
