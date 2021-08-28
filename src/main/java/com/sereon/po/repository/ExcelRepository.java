package com.sereon.po.repository;

import com.sereon.po.entity.ExcelCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ExcelRepository extends JpaRepository<ExcelCell, String> {
    List<ExcelCell> findBySqlID(String sqlID);
}
