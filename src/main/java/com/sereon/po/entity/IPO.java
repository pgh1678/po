package com.sereon.po.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IPO extends BaseEntity{

    @Id
    @Column(length = 10)
    private String stockCode;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 8, nullable = true)
    private String startDate;

    @Column(length = 8, nullable = true)
    private String endDate;

    @Column(length = 8, nullable = true)
    private String listedDate;

    @Column(length = 8, nullable = true)
    private String refundDate;

    private Long parValue;

    private Long offeringPrice;

    @Column(length = 50, nullable = true)
    private String leadingFirm;

    @Column(length = 255, nullable = true)
    private String detailURL;

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setListedDate(String listedDate) {
        this.listedDate = listedDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public void setParValue(Long parValue) {
        this.parValue = parValue;
    }

    public void setOfferingPrice(Long offeringPrice) {
        this.offeringPrice = offeringPrice;
    }

    public void setLeadingFirm(String leadingFirm) {
        this.leadingFirm = leadingFirm;
    }
}
