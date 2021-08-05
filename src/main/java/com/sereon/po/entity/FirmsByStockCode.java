package com.sereon.po.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@IdClass(FirmsID.class)
public class FirmsByStockCode extends BaseEntity implements Serializable{

    @Id
    @Column(name = "stock_code")
    private String stockCode;

    @Id
    @Column(name = "firm_name")
    private String firmName;


    private Long assignAmt;

    private Long limitAmt;

    @Column(length = 100)
    private String RMK;
}

