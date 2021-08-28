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
public class MyIPOExcelDTO {

    public Long sno;

    public String userId;

    public Long deposit;

    public Long subsAmt;

    public Long assignAmt;

    public Long sellPrice;

    public Long fee;

    public Long tax;

    public Long interest;

    public String stockFirm;

    public String name;

    public String startDate;

    public String endDate;

    public String listedDate;

    public String refundDate;

    public Long offeringPrice;

    public String stockCode;

    public Long loan;

    public Long buyAmt;

    public Long refundAmt;

    public Long profit;

    public double profitRate;

    public Long totalMoney;


    public LocalDateTime regDate, chgDate;
}
