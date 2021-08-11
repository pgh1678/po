package com.sereon.po.repository;

import com.querydsl.jpa.OpenJPATemplates;
import com.sereon.po.entity.FirmsByStockCode;
import com.sereon.po.entity.IPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FirmsRepository extends JpaRepository<FirmsByStockCode, String> {

    @Query("select f from FirmsByStockCode f where f.stockCode = :stockCode order by assignAmt desc")
    List<FirmsByStockCode> findByStockCode(@Param("stockCode") String stockCode);


    @Query("select f from Subscription s join FirmsByStockCode f on f.stockCode = s.IPO.stockCode where s.sno = :sno")
    List<FirmsByStockCode> findBySno(@Param("sno") Long sno);


}