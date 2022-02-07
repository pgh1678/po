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

    private Long unlistedPrice;

    @Column(length = 50, nullable = true)
    private String leadingFirm;

    @Column(length = 255, nullable = true)
    private String detailURL;

    @Column(length = 2, nullable = true)
    private String type;

    @Column(length = 1, nullable = true)
    private String dropYN;

    private Long nowVal;

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
