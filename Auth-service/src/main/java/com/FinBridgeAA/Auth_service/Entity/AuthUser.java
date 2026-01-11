package com.FinBridgeAA.Auth_service.Entity;


import com.FinBridgeAA.Auth_service.Enums.Role;
import com.FinBridgeAA.Auth_service.Enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, name="auth_user_id")
    private UUID authUserId;
    @Column(name="phone_number", nullable=false)
    private String phoneNumber;
    @Column(name="otp_hash")
    private String otpHash;
    @Column(name="otp_expiry")
    private LocalDateTime otpExpiry;
    @Column(name = "otp_attempts")
    private int OtpAttempts;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;
}
