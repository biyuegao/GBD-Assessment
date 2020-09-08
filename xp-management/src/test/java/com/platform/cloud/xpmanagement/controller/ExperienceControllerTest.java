package com.platform.cloud.xpmanagement.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.cloud.xpmanagement.domain.ApiRequest;
import com.platform.cloud.xpmanagement.service.impl.ExperienceServiceImpl;
import com.platform.cloud.xpmanagement.utils.TestDataUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {ExperienceController.class, ExperienceServiceImpl.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class ExperienceControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExperienceServiceImpl experienceService;

    @Captor
    private ArgumentCaptor<Integer> playerIdCaptor;

    @Captor
    private ArgumentCaptor<Integer> pointsCaptor;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenValidPlayerId_thenReturnOk() throws Exception {
        when(experienceService.getExperienceResponse(anyInt())).thenReturn(TestDataUtils.getExperienceResponse());
        mvc.perform(get("/experience-endpoint/v1/experience/1")).andExpect(status().isOk());
        verify(experienceService).getExperienceResponse(playerIdCaptor.capture());
        assertEquals(1, playerIdCaptor.getValue());
    }

    @Test
    public void givenInvalidPlayerId_thenReturnBadRequest() throws Exception {
        mvc.perform(get("/experience-endpoint/v1/experience/abc")).andExpect(status().isBadRequest());
        verify(experienceService, times(0)).getExperienceResponse(anyInt());
    }

    @Test
    public void givenValidPlayerId_GotRunTimeException_thenReturnInternalServerError() throws Exception {
        when(experienceService.getExperienceResponse(anyInt())).thenThrow(RuntimeException.class);
        mvc.perform(get("/experience-endpoint/v1/experience/1")).andExpect(status().isInternalServerError());
        verify(experienceService).getExperienceResponse(playerIdCaptor.capture());
        assertEquals(1, playerIdCaptor.getValue());
    }

    @Test
    public void givenValidPlayerIdAndValidPoints_thenReturnOk() throws Exception {
        ApiRequest request = ApiRequest.builder().points(5).build();
        doNothing().when(experienceService).addExperienceLogAndUpdateExperience(anyInt(), anyInt());
        mvc.perform(post("/experience-endpoint/v1/experience/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request))).andExpect(status().isCreated());
        verify(experienceService).addExperienceLogAndUpdateExperience(playerIdCaptor.capture(), pointsCaptor.capture());
        assertEquals(1, playerIdCaptor.getValue());
        assertEquals(5, pointsCaptor.getValue());
    }

    @Test
    public void givenInvalidPlayerIdAndValidPoints_thenReturnBadRequest() throws Exception {
        ApiRequest request = ApiRequest.builder().points(5).build();
        mvc.perform(post("/experience-endpoint/v1/experience/abc").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request))).andExpect(status().isBadRequest());
        verify(experienceService, times(0)).addExperienceLogAndUpdateExperience(anyInt(), anyInt());
    }

    @Test
    public void givenValidPlayerIdAndValidPoints_GotRunTimeException_thenReturnInternalServerError() throws Exception {
        ApiRequest request = ApiRequest.builder().points(5).build();
        doThrow(RuntimeException.class).when(experienceService).addExperienceLogAndUpdateExperience(anyInt(), anyInt());
        mvc.perform(post("/experience-endpoint/v1/experience/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request))).andExpect(status().isInternalServerError());
        verify(experienceService).addExperienceLogAndUpdateExperience(playerIdCaptor.capture(), pointsCaptor.capture());
        assertEquals(1, playerIdCaptor.getValue());
        assertEquals(5, pointsCaptor.getValue());
    }
}
