package com.sereon.po.repository;

import com.sereon.po.entity.IPO;
import com.sereon.po.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("select s, i from Subscription s inner join IPO i on i = s.IPO.stockCode where i.listedDate between coalesce(:fromDt,'20200101') and :toDt")
    Page<Object[]> findAllByListedDateBetween(Pageable pageable, @Param("fromDt") String fromDt, @Param("toDt")String toDt);

    @Query("select s, i from Subscription s join IPO i on i = s.IPO.stockCode where  i.startDate between coalesce(:fromDt,'20200101') and :toDt")
    Page<Object[]> findAllByStartDateBetween(Pageable pageable, @Param("fromDt") String fromDt, @Param("toDt") String toDt);

    @Query("select s, i from Subscription s join IPO i on i = s.IPO.stockCode where  i.endDate between coalesce(:fromDt,'20200101') and :toDt")
    Page<Object[]> findAllByEndDateBetween(Pageable pageable, @Param("fromDt") String fromDt, @Param("toDt") String toDt);

    @Query("select s, i from Subscription s join IPO i on i = s.IPO.stockCode where s.userId=:userId")
    Page<Object[]> findAllByUserId(Pageable pageable,@Param("userId") String userId);

}
