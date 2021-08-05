package com.sereon.po.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "IPO")
public class Subscription extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    @Column(length = 100, nullable = true)
    private String userId;

    @Column(nullable = false)
    private Long deposit;

    @Column(nullable = false)
    private Long subsAmt;

    @Column(nullable = true)
    @ColumnDefault("0")
    private Long assignAmt;

    @Column(nullable = true)
    @ColumnDefault("0")
    private Long sellPrice;

    @Column(nullable = true)
    @ColumnDefault("0")
    private Long fee;

    @Column(nullable = true)
    @ColumnDefault("0")
    private Long tax;

    @Column(nullable = true)
    @ColumnDefault("0")
    private Long interest;

    @Column(nullable = false)
    private String stockFirm;

    @Column(length = 1, nullable = true)
    private String monitoringYN;


    @ManyToOne(fetch = FetchType.LAZY)
    private IPO IPO;

    public void setSno(Long sno) {
        this.sno = sno;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDeposit(Long deposit) {
        this.deposit = deposit;
    }

    public void setSubsAmt(Long subsAmt) {
        this.subsAmt = subsAmt;
    }

    public void setAssignAmt(Long assignAmt) {
        this.assignAmt = assignAmt;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public void setStockFirm(String stockFirm) {
        this.stockFirm = stockFirm;
    }

    public void setIPO(IPO IPO) {
        this.IPO = IPO;
    }

    public void setInterest(Long interest) {this.interest = interest;    }
}
