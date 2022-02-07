package com.sereon.po.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String type;
    private String keyword;
    private int year;
    private int month;
    private String fromDt, toDt;
    private String userId;

    public PageRequestDTO(){
        this.page = 1;
        this.size = 20;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        year = Integer.parseInt(sdf.format(date));

        sdf = new SimpleDateFormat("MM");
        month = Integer.parseInt(sdf.format(date));

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        toDt = sdf.format(date);
        fromDt = year+"-01-01";

        System.out.println(toDt);

    }

    public PageRequestDTO(int size){
        this.page = 1;
        this.size = size;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        year = Integer.parseInt(sdf.format(date));

        sdf = new SimpleDateFormat("MM");
        month = Integer.parseInt(sdf.format(date));

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        toDt = sdf.format(date);

        System.out.println(toDt);

    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1,size,sort);
    }
}
