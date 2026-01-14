package com.FinBridgeAA.consent_service.Service;

import com.FinBridgeAA.consent_service.DTO.*;
import com.FinBridgeAA.consent_service.Entity.Consent;
import com.FinBridgeAA.consent_service.Enums.ConsentStatus;
import com.FinBridgeAA.consent_service.Repository.ConsentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsentService {

    private final ConsentRepository consentRepository;
    private final AAGatewayService aaGatewayService;


    public ConsentCreateResponseDTO createConsent(ConsentCreateRequestDTO dto) {

        Consent consent = new Consent();
        consent.setUserId(dto.getUserId());
        consent.setPurpose(dto.getPurpose());
        consent.setDataType(dto.getDataType());
        consent.setFromDate(dto.getFromDate());
        consent.setToDate(dto.getToDate());
        consent.setStatus(ConsentStatus.PENDING);

        Consent saved = consentRepository.save(consent);

        // Send consent to AA
        String aaConsentId = aaGatewayService.createConsentInAA(saved);

        saved.setAaConsentId(aaConsentId);
        consentRepository.save(saved);

        return new ConsentCreateResponseDTO(
                saved.getConsentId().toString(),
                saved.getStatus().name()
        );
    }

    public ConsentStatusResponseDTO getStatus(UUID consentId) {

        Consent consent = consentRepository.findById(consentId)
                .orElseThrow(() -> new RuntimeException("Consent not found"));

        return new ConsentStatusResponseDTO(
                consent.getConsentId().toString(),
                consent.getStatus().name()
        );
    }

    // Validate consent (FOR FIU)
    public ConsentValidateResponseDTO validateConsent(ConsentValidateRequestDTO dto) {

        UUID consentId = UUID.fromString(dto.getConsentId());

        Consent consent = consentRepository.findById(consentId)
                .orElseThrow(() -> new RuntimeException("Consent not found"));

        if (consent.getStatus() != ConsentStatus.APPROVED) {
            return new ConsentValidateResponseDTO(false, "Consent not approved");
        }

        if (consent.getExpiresAt().isBefore(Instant.now())) {
            return new ConsentValidateResponseDTO(false, "Consent expired");
        }

        if (consent.getPurpose() != dto.getPurpose()) {
            return new ConsentValidateResponseDTO(false, "Consent purpose mismatch");
        }

        return new ConsentValidateResponseDTO(true, "Consent valid");
    }

    public void revokeConsent(UUID consentId) {

        Consent consent = consentRepository.findById(consentId)
                .orElseThrow(() -> new RuntimeException("Consent not found"));

        consent.setStatus(ConsentStatus.REVOKED);
        consentRepository.save(consent);
    }
}