package com.sereon.po.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

    @Column(nullable = true)
    @ColumnDefault("0")
    private Long loan;

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

}
