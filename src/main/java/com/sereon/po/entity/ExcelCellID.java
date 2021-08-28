package com.sereon.po.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
class ExcelCellID implements Serializable {

    @Column(length = 10)
    private String sqlID;

    private int columnNum;


}
