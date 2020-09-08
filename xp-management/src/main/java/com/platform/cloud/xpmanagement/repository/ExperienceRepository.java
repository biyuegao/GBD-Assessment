package com.platform.cloud.xpmanagement.repository;

import com.platform.cloud.xpmanagement.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    Optional<Experience> findByPlayerId(int playerId);
}
