package com.platform.cloud.xpmanagement.service;

import com.platform.cloud.xpmanagement.domain.ExperienceLogType;
import com.platform.cloud.xpmanagement.domain.ExperienceResponse;
import com.platform.cloud.xpmanagement.entity.Experience;
import com.platform.cloud.xpmanagement.entity.ExperienceLog;
import com.platform.cloud.xpmanagement.repository.ExperienceLogRepository;
import com.platform.cloud.xpmanagement.repository.ExperienceRepository;
import com.platform.cloud.xpmanagement.service.impl.ExperienceServiceImpl;
import com.platform.cloud.xpmanagement.utils.TestDataUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExperienceServiceTest {
    private ExperienceService experienceService;

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private ExperienceLogRepository experienceLogRepository;

    @Captor
    private ArgumentCaptor<Experience> experienceArgumentCaptor;

    @Captor
    private ArgumentCaptor<ExperienceLog> experienceLogArgumentCaptor;

    @Before
    public void setUp() {
        experienceService = new ExperienceServiceImpl(experienceRepository, experienceLogRepository);
    }

    @Test
    public void getExperience_whenExperienceNotExist_thenCreateExperience() {
        when(experienceRepository.findByPlayerId(anyInt())).thenReturn(Optional.empty());
        when(experienceRepository.saveAndFlush(any())).thenReturn(new Experience());

        ExperienceResponse experienceResponse = experienceService.getExperienceResponse(1);

        verify(experienceRepository).findByPlayerId(anyInt());
        verify(experienceRepository).saveAndFlush(experienceArgumentCaptor.capture());
        assertNotNull(experienceResponse);
        assertEquals(1, experienceArgumentCaptor.getValue().getPlayerId());
        assertEquals(0, experienceArgumentCaptor.getValue().getBalance());
        assertEquals(0, experienceResponse.getBalance());
    }

    @Test
    public void getExperience_whenExperienceIsExist_thenNoNeedCreateExperience() {
        when(experienceRepository.findByPlayerId(anyInt())).thenReturn(Optional.of(TestDataUtils.getExperience()));

        ExperienceResponse experienceResponse = experienceService.getExperienceResponse(1);

        verify(experienceRepository).findByPlayerId(anyInt());
        verify(experienceRepository, times(0)).saveAndFlush(any());
        assertEquals(TestDataUtils.getExperience().getBalance(), experienceResponse.getBalance());
    }

    @Test
    public void createExperienceLogAndUpdateExperience_whenExperienceNotExistAndPointsMoreThan0_thenTypeIsEarn() {
        when(experienceRepository.findByPlayerId(anyInt())).thenReturn(Optional.empty());
        when(experienceRepository.saveAndFlush(any())).thenReturn(new Experience());
        when(experienceLogRepository.saveAndFlush(any())).thenReturn(ExperienceLog.builder().build());

        experienceService.addExperienceLogAndUpdateExperience(1, 5);

        verify(experienceRepository).findByPlayerId(anyInt());
        verify(experienceRepository, times(2)).saveAndFlush(any());
        verify(experienceLogRepository).saveAndFlush(experienceLogArgumentCaptor.capture());
        assertEquals(1, experienceLogArgumentCaptor.getValue().getPlayerId());
        assertEquals(5, experienceLogArgumentCaptor.getValue().getAmount());
        assertEquals(ExperienceLogType.EARN.name(), experienceLogArgumentCaptor.getValue().getType());
        assertEquals("Earn 5 points", experienceLogArgumentCaptor.getValue().getRemarks());
    }

    @Test
    public void createExperienceLogAndUpdateExperience_whenExperienceNotExistAndPointsLessThan0_thenTypeIsPenalty() {
        when(experienceRepository.findByPlayerId(anyInt())).thenReturn(Optional.empty());
        when(experienceRepository.saveAndFlush(any())).thenReturn(new Experience());
        when(experienceLogRepository.saveAndFlush(any())).thenReturn(ExperienceLog.builder().build());

        experienceService.addExperienceLogAndUpdateExperience(1, -2);

        verify(experienceRepository).findByPlayerId(anyInt());
        verify(experienceRepository, times(2)).saveAndFlush(any());
        verify(experienceLogRepository).saveAndFlush(experienceLogArgumentCaptor.capture());
        assertEquals(1, experienceLogArgumentCaptor.getValue().getPlayerId());
        assertEquals(-2, experienceLogArgumentCaptor.getValue().getAmount());
        assertEquals(ExperienceLogType.PENALTY.name(), experienceLogArgumentCaptor.getValue().getType());
        assertEquals("Penalty 2 points", experienceLogArgumentCaptor.getValue().getRemarks());
    }

    @Test
    public void createExperienceLogAndUpdateExperience_whenExperienceIsExistAndPointsMoreThan0_thenTypeIsEarn() {
        when(experienceRepository.findByPlayerId(anyInt())).thenReturn(Optional.of(TestDataUtils.getExperience()));
        when(experienceRepository.saveAndFlush(any())).thenReturn(new Experience());
        when(experienceLogRepository.saveAndFlush(any())).thenReturn(ExperienceLog.builder().build());

        experienceService.addExperienceLogAndUpdateExperience(1, 5);

        verify(experienceRepository).findByPlayerId(anyInt());
        verify(experienceRepository).saveAndFlush(any());
        verify(experienceLogRepository).saveAndFlush(experienceLogArgumentCaptor.capture());
        assertEquals(1, experienceLogArgumentCaptor.getValue().getPlayerId());
        assertEquals(5, experienceLogArgumentCaptor.getValue().getAmount());
        assertEquals(ExperienceLogType.EARN.name(), experienceLogArgumentCaptor.getValue().getType());
        assertEquals("Earn 5 points", experienceLogArgumentCaptor.getValue().getRemarks());
    }

    @Test
    public void createExperienceLogAndUpdateExperience_whenExperienceIsExistAndPointsLessThan0_thenTypeIsPenalty() {
        when(experienceRepository.findByPlayerId(anyInt())).thenReturn(Optional.of(TestDataUtils.getExperience()));
        when(experienceRepository.saveAndFlush(any())).thenReturn(new Experience());
        when(experienceLogRepository.saveAndFlush(any())).thenReturn(ExperienceLog.builder().build());

        experienceService.addExperienceLogAndUpdateExperience(1, -2);

        verify(experienceRepository).findByPlayerId(anyInt());
        verify(experienceRepository).saveAndFlush(any());
        verify(experienceLogRepository).saveAndFlush(experienceLogArgumentCaptor.capture());
        assertEquals(1, experienceLogArgumentCaptor.getValue().getPlayerId());
        assertEquals(-2, experienceLogArgumentCaptor.getValue().getAmount());
        assertEquals(ExperienceLogType.PENALTY.name(), experienceLogArgumentCaptor.getValue().getType());
        assertEquals("Penalty 2 points", experienceLogArgumentCaptor.getValue().getRemarks());
    }
}
