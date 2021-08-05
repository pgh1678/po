package com.sereon.po.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class FirmsDTO {
    private String stockCode;
    private String firmName;
    private Long assignAmt;
    private Long limitAmt;
    private String RMK;
}