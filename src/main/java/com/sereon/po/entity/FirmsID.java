package com.sereon.po.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
class FirmsID implements Serializable {

    @Column(length = 10)
    private String stockCode;

    @Column(length = 100)
    private String firmName;
}
