package com.platform.cloud.xpmanagement.utils;

import com.platform.cloud.xpmanagement.domain.ExperienceLogType;
import com.platform.cloud.xpmanagement.domain.ExperienceResponse;
import com.platform.cloud.xpmanagement.entity.Experience;
import com.platform.cloud.xpmanagement.entity.ExperienceLog;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TestDataUtils {
    public static Experience getExperience() {
        Experience experience = new Experience();
        experience.setPlayerId(1);
        experience.setBalance(100);
        experience.setCreatedAtTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        experience.setUpdatedAtTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return experience;
    }

    public static ExperienceLog getExperienceLog(int experienceId) {
        return ExperienceLog.builder()
                .experienceLogId(UuidUtil.getTimeBasedUuid().toString())
                .experienceId(experienceId)
                .playerId(2)
                .amount(5)
                .type(ExperienceLogType.EARN.name())
                .remarks("Earn 5 points")
                .createdAtTimestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

    public static ExperienceResponse getExperienceResponse() {
        return ExperienceResponse.builder()
                .balance(100)
                .createdAtTimestamp("2020-09-07 10:10:00")
                .updatedAtTimestamp("2020-09-07 10:10:00")
                .build();
    }
}
