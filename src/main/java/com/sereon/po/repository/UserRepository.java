package com.sereon.po.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sereon.po.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.email=:email and u.fromSocial = :social")
    Optional<User> findByEmail(@Param("email") String email, @Param("social") boolean social);
}
