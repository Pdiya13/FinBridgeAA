package com.FinBridgeAA.consent_service.Controller;

import com.FinBridgeAA.consent_service.DTO.*;
import com.FinBridgeAA.consent_service.Service.ConsentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/consents")
@RequiredArgsConstructor
public class ConsentController {

    private final ConsentService consentService;

    @PostMapping
    public ConsentCreateResponseDTO createConsent(
            @Valid @RequestBody ConsentCreateRequestDTO dto
    ) {
        return consentService.createConsent(dto);
    }

    @GetMapping("/{consentId}/status")
    public ConsentStatusResponseDTO getStatus(
            @PathVariable String consentId
    ) {
        UUID uuid = UUID.fromString(consentId);
        return consentService.getStatus(uuid);
    }

    @PostMapping("/validate")
    public ConsentValidateResponseDTO validateConsent(
            @RequestBody ConsentValidateRequestDTO dto
    ) {
        return consentService.validateConsent(dto);
    }

    @PostMapping("/{consentId}/revoke")
    public void revokeConsent(
            @PathVariable String consentId
    ) {
        UUID uuid = UUID.fromString(consentId);
        consentService.revokeConsent(uuid);
    }
}