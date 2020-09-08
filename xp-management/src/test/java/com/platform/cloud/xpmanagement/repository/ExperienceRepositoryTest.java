package com.platform.cloud.xpmanagement.repository;

import com.platform.cloud.xpmanagement.entity.Experience;
import com.platform.cloud.xpmanagement.utils.TestDataUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ExperienceRepositoryTest {
    @Autowired
    private ExperienceRepository experienceRepository;

    private Experience experience;

    @Before
    public void setUp() {
        experience = TestDataUtils.getExperience();
        experience = experienceRepository.saveAndFlush(experience);
    }

    @Test
    public void test_experience_search() {
        Optional<Experience> result = experienceRepository.findByPlayerId(experience.getPlayerId());
        assertTrue(result.isPresent());
        assertEquals(experience, result.get());
    }

    @After
    public void delete() {
        experienceRepository.delete(experience);
    }
}
