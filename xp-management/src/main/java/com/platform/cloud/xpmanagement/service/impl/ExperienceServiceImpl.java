package com.platform.cloud.xpmanagement.service.impl;

import com.platform.cloud.xpmanagement.domain.ExperienceLogType;
import com.platform.cloud.xpmanagement.domain.ExperienceResponse;
import com.platform.cloud.xpmanagement.entity.Experience;
import com.platform.cloud.xpmanagement.entity.ExperienceLog;
import com.platform.cloud.xpmanagement.repository.ExperienceLogRepository;
import com.platform.cloud.xpmanagement.repository.ExperienceRepository;
import com.platform.cloud.xpmanagement.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceLogRepository experienceLogRepository;

    @Override
    @Transactional
    public ExperienceResponse getExperienceResponse(int playerId) {
        Experience experience = getExperience(playerId);
        return ExperienceResponse.builder()
                .balance(experience.getBalance())
                .createdAtTimestamp(experience.getCreatedAtTimestamp().toString())
                .updatedAtTimestamp(experience.getUpdatedAtTimestamp().toString())
                .build();
    }

    @Override
    @Transactional
    public void addExperienceLogAndUpdateExperience(int playerId, int points) {
        Experience experience = getExperience(playerId);
        ExperienceLogType experienceLogType = ExperienceLogType.EARN;
        String remarks;
        if(points >= 0) {
            remarks = "Earn " + points + " points";
        } else {
            experienceLogType = ExperienceLogType.PENALTY;
            remarks = "Penalty " + Math.abs(points) + " points";
        }
        ExperienceLog experienceLog = ExperienceLog.builder()
                .experienceLogId(UuidUtil.getTimeBasedUuid().toString())
                .experienceId(experience.getExperienceId())
                .playerId(playerId)
                .amount(points)
                .type(experienceLogType.name())
                .remarks(remarks)
                .createdAtTimestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        experienceLogRepository.saveAndFlush(experienceLog);
        List<Integer> amountList = experienceLogRepository.findAmountByExperienceId(experience.getExperienceId());
        int balance = amountList.stream().reduce(0, Integer::sum);
        experience.setBalance(balance);
        experience.setUpdatedAtTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        experienceRepository.saveAndFlush(experience);
    }

    private Experience getExperience(int playerId) {
        Optional<Experience> experienceOptional = experienceRepository.findByPlayerId(playerId);
        Experience experience;
        if(experienceOptional.isPresent()) {
            experience = experienceOptional.get();
        } else {
            experience = new Experience();
            experience.setPlayerId(playerId);
            experience.setBalance(0);
            experience.setCreatedAtTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            experience.setUpdatedAtTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            experienceRepository.saveAndFlush(experience);
        }
        return experience;
    }


}
