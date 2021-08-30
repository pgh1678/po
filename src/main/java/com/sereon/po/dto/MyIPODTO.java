package com.sereon.po.dto;

import com.sereon.po.entity.IPO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class MyIPODTO {

    private Long sno;

    private String userId;

    private Long deposit;

    private Long subsAmt;

    private Long assignAmt;

    private Long sellPrice;

    private Long fee;

    private Long tax;

    private Long interest;

    private String stockFirm;

    private String name;

    private String startDate;

    private String endDate;

    private String listedDate;

    private String refundDate;

    private Long parValue;

    private Long offeringPrice;

    private IPO IPO;

    private String stockCode;

    private Long loan;

    private Long nowVal;

    private LocalDateTime regDate, chgDate;
}
