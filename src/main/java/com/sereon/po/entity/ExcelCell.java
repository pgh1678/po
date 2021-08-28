package com.sereon.po.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import org.apache.poi.ss.usermodel.CellType;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@IdClass(ExcelCellID.class)
public class ExcelCell extends BaseEntity implements Serializable{

    @Id
    @Column(name = "sql_id")
    private String sqlID;

    @Id
    @Column(name = "column_num")
    private int columnNum;

    @Column(length = 255)
    private String columnName;

    @Column(length = 255)
    private String columnId;

    private int columnWidth;

    private char cellType;

    public CellType getCellType(){
        CellType ret = null;

        if(cellType == 'N'){
            ret = CellType.NUMERIC;
        }else if(cellType == 'T'){
            ret = CellType.STRING;
        }else if(cellType == 'B'){
            ret = CellType.BOOLEAN;
        }

        return ret;
    }
}

