package com.sereon.po.service;


import com.sereon.po.entity.ExcelCell;
import com.sereon.po.repository.ExcelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExcelServiceImpl implements ExcelService{

    private final ExcelRepository excelRepository;

    @Override
    public List<ExcelCell> getCellProp(String sqlID){
        log.info("getCellProp : "+sqlID);

        List<ExcelCell> result = excelRepository.findBySqlID(sqlID);

        log.info(result);

        return result;
    }

    @Override
    public XSSFWorkbook createExcel(String title, List<ExcelCell> cellProp, List<Object> cellValues)  {
        log.info("Excel Create!!");
        XSSFWorkbook xlsxWB = new XSSFWorkbook();


        // Sheet 이름 설정하기
        XSSFSheet sheet1 = xlsxWB.createSheet(title);
        sheet1.setFitToPage(true);
        sheet1.setAutobreaks(true);

        sheet1.setMargin(Sheet.LeftMargin, 0.5 /* inches */ );
        sheet1.setMargin(Sheet.RightMargin, 0.5 /* inches */ );
        sheet1.setRepeatingRows(CellRangeAddress.valueOf("1"));


        // 컬럼의 너비 설정
//		sheet1.autoSizeColumn(1); // 행 번호

        //타이틀 Cell 스타일
        XSSFCellStyle cellStyle = xlsxWB.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);

        //타이틀 Cell 색상
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); // 밝은 Grey
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cellStyle.setWrapText(true); //줄바뀜처리
        cellStyle.setAlignment(HorizontalAlignment.CENTER);  //가로정렬
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //세로정렬


        //내용 Cell 스타일
        XSSFCellStyle cellStyle2 = xlsxWB.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //세로정렬

        cellStyle2.setBorderBottom(BorderStyle.THIN);
        cellStyle2.setBorderTop(BorderStyle.THIN);
        cellStyle2.setBorderRight(BorderStyle.THIN);
        cellStyle2.setBorderLeft(BorderStyle.THIN);

        //내용은 크기에 따른 줄 바꿈 필요없음.(Default)
        //cellStyle2.setWrapText(true);
        Font font = xlsxWB.createFont();
        font.setFontHeightInPoints((short) 10);
        cellStyle2.setFont(font);

        makeHeader(sheet1, cellProp,cellStyle);

        int i = 1;
        for(Object cellValue : cellValues) {
            log.info(cellValue);
            makeBody(sheet1, cellProp, cellValue, cellStyle2,  i++);
        }
        System.out.println("row : "+i);
        return xlsxWB;
    }

    private void makeHeader(XSSFSheet sheet, List<ExcelCell> cellProp, XSSFCellStyle cellStyle) {
        //헤더 ROW 생성
        XSSFRow headerRow = sheet.createRow(0);
        for(ExcelCell cols : cellProp){
            sheet.setColumnWidth(cols.getColumnNum(), cols.getColumnWidth());
            XSSFCell cell = headerRow.createCell(cols.getColumnNum(), CellType.STRING);
            cell.setCellValue(cols.getColumnName());
            cell.setCellStyle(cellStyle);
        }
    }

    private void makeBody(XSSFSheet sheet,List<ExcelCell> cellProp, Object cellValue, XSSFCellStyle cellStyle , int index){
        XSSFRow row = sheet.createRow(index);

        try {
            for (ExcelCell cols : cellProp) {

                Field field = cellValue.getClass().getField(cols.getColumnId());
                XSSFCell cell = row.createCell(cols.getColumnNum());
                field.setAccessible(true);
                if(field.get(cellValue) instanceof Number){
                    Number numberValue = (Number) field.get(cellValue);
                    cell.setCellValue(numberValue.doubleValue());
                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
                }
                else {
                    cell.setCellValue(field.get(cellValue)==null?"":field.get(cellValue).toString());
                    cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(""));
                }
                cell.setCellType(cols.getCellType());
                cell.setCellStyle(cellStyle);

            }
        }catch (NoSuchFieldException | IllegalAccessException e ){
            System.out.println(e.getStackTrace().toString());
        }

    }
}
