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

    @Query("select s, i from Subscription s join IPO i on i = s.IPO.stockCode where s.userId=:userId order by i.listedDate desc")
    List<Object[]> findAllByUserId(@Param("userId") String userId);

    @Query("SELECT sum((coalesce(s.sellPrice,0)-coalesce(i.offeringPrice,0)) * coalesce(s.assignAmt,0) - coalesce(s.fee,0) - coalesce(s.tax,0) - coalesce(s.interest,0)) FROM Subscription s JOIN IPO i ON i = s.IPO.stockCode WHERE s.userId =:userId AND i.listedDate between coalesce(:fromDt,'20200101') and :toDt and coalesce(s.sellPrice,0) <> 0")
    Long findYTD(@Param("userId") String userId,@Param("fromDt") String fromDt, @Param("toDt") String toDt);

    @Query("SELECT max(s.deposit) FROM Subscription s JOIN IPO i ON i = s.IPO.stockCode WHERE s.userId =:userId AND i.listedDate between coalesce(:fromDt,'20200101') and :toDt")
    Long findMaxDeposit(@Param("userId") String userId, @Param("fromDt") String fromDt, @Param("toDt") String toDt);


}
