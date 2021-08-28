package com.sereon.po.service;


import com.sereon.po.dto.*;
import com.sereon.po.entity.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Optional;

public interface ExcelService {

    List<ExcelCell> getCellProp(String sqlID);
    XSSFWorkbook createExcel(String title, List<ExcelCell> cellProp, List<Object> cellValue);
}
