package com.sereon.po.entity;


import lombok.*;

import javax.persistence.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MyAccount extends BaseEntity {

    @Id
    @Column(length = 100)
    private String email;

    private String bankName;

    private Long investmentMoney;

    private float interestRate;
}