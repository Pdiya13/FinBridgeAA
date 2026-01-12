package com.FinBridgeAA.consent_service.Entity;

import com.FinBridgeAA.consent_service.Enums.ConsentPurpose;
import com.FinBridgeAA.consent_service.Enums.ConsentStatus;
import com.FinBridgeAA.consent_service.Enums.FinancialDataType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "consents")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false,updatable = false)
    private UUID consentId;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsentPurpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FinancialDataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsentStatus status;

    @Column(nullable = false)
    private LocalDate fromData;

    @Column(nullable = false)
    private LocalDate toDate;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();

        if (this.status == null) {
            this.status = ConsentStatus.PENDING;
        }

        if (this.expiresAt == null) {
            this.expiresAt = Instant.now().plusSeconds(30L * 24 * 60 * 60); // 30 days
        }
    }

}