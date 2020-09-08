package com.platform.cloud.xpmanagement.repository;

import com.platform.cloud.xpmanagement.entity.Experience;
import com.platform.cloud.xpmanagement.entity.ExperienceLog;
import com.platform.cloud.xpmanagement.utils.TestDataUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("Test")
public class ExperienceLogRepositoryTest {
    @Autowired
    private ExperienceLogRepository experienceLogRepository;
    @Autowired
    private ExperienceRepository experienceRepository;

    private ExperienceLog experienceLog;
    private Experience experience;

    @Before
    public void setUp() {
        experience = TestDataUtils.getExperience();
        experience = experienceRepository.saveAndFlush(experience);
        experienceLog = TestDataUtils.getExperienceLog(experience.getExperienceId());
        experienceLogRepository.saveAndFlush(experienceLog);
    }

    @Test
    public void test_experience_search() {
        List<Integer> result = experienceLogRepository.findAmountByExperienceId(experienceLog.getExperienceId());
        assertEquals(1, result.size());
        assertEquals(5, result.get(0));
    }

    @After
    public void delete() {
        experienceLogRepository.delete(experienceLog);
        experienceRepository.delete(experience);
    }
}
