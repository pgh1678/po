package com.sereon.po.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AccountDTO {
    private String email;
    private String bankName;
    private Long investmentMoney;
    private float interestRate;

}