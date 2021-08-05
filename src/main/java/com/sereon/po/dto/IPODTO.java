package com.sereon.po.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class IPODTO {

    private String stockCode;

    private String name;

    private String startDate;

    private String endDate;

    private String listedDate;

    private String refundDate;

    private Long parValue;

    private Long offeringPrice;

    private String leadingFirm;



    private LocalDateTime regDate, chgDate;
}
