package com.FinBridgeAA.consent_service.DTO;

import com.FinBridgeAA.consent_service.Enums.ConsentPurpose;
import com.FinBridgeAA.consent_service.Enums.FinancialDataType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ConsentCreateRequestDTO {
    private String userId;
    private ConsentPurpose purpose;
    private FinancialDataType dataType;
    private LocalDate fromDate;
    private LocalDate toDate;
}