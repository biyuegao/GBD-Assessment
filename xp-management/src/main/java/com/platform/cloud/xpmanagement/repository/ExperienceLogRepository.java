package com.platform.cloud.xpmanagement.repository;

import com.platform.cloud.xpmanagement.entity.ExperienceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceLogRepository extends JpaRepository<ExperienceLog, String> {
    @Query("select eg.amount from ExperienceLog eg where eg.experienceId = :experienceId")
    List<Integer> findAmountByExperienceId(@Param("experienceId") int experienceId);
}
