package com.platform.cloud.xpmanagement.service;

import com.platform.cloud.xpmanagement.domain.ExperienceResponse;

public interface ExperienceService {
    ExperienceResponse getExperienceResponse(int playerId);
    void addExperienceLogAndUpdateExperience(int playerId, int points);
}
