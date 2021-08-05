package com.sereon.po.repository;

import com.querydsl.jpa.OpenJPATemplates;
import com.sereon.po.entity.IPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IPORepository extends JpaRepository<IPO, String> {

    @Query("select i from IPO i where start_date like :YYYYMM||'%'")
    Page<IPO> findAllM(Pageable pageable,@Param("YYYYMM") String YYYYMM);

    @Query("select i from IPO i where i.stockCode = :stockCode")
    Optional<IPO> getIPOByStockCode(String stockCode);
}
