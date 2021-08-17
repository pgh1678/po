package com.sereon.po.repository;

import com.querydsl.jpa.OpenJPATemplates;
import com.sereon.po.entity.IPO;
import com.sereon.po.entity.MyAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MyAccountRepository extends JpaRepository<MyAccount, String> {

}