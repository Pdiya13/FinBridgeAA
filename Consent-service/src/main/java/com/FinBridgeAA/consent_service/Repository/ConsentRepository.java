package com.FinBridgeAA.consent_service.Repository;

import com.FinBridgeAA.consent_service.Entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsentRepository extends JpaRepository<Consent, UUID> {
}