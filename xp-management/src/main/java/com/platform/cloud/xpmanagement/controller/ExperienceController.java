package com.platform.cloud.xpmanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.cloud.xpmanagement.common.PlayerId;
import com.platform.cloud.xpmanagement.domain.ApiRequest;
import com.platform.cloud.xpmanagement.domain.ExperienceResponse;
import com.platform.cloud.xpmanagement.service.impl.ExperienceServiceImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/experience-endpoint/v1")
@Tag(name = "experience-endpoint", description = "Rest api for experience and experience log search, save and update")
@RequiredArgsConstructor
@Slf4j
public class ExperienceController {
    private final ExperienceServiceImpl experienceServiceImpl;

    @GetMapping("/experience/{player_id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad input parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> getExperienceBalance(@PlayerId @PathVariable("player_id") int playerId) {
        try {
            ExperienceResponse experienceResponse = experienceServiceImpl.getExperienceResponse(playerId);
            return ResponseEntity.ok(experienceResponse);
        } catch (RuntimeException e) {
            log.info("Get exception when get experience: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

//    @RequestMapping(path = "/experience/{player_id}", consumes = "application/json", produces = "application/json")
    @PostMapping("/experience/{player_id}")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "item created"),
            @ApiResponse(responseCode = "400", description = "Bad input parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> updateExperienceBalance(@PlayerId@PathVariable("player_id") int playerId, @RequestBody ApiRequest request) {
        try {
            log.info("Get playerId={} and points={}.", playerId, request.getPoints());
            experienceServiceImpl.addExperienceLogAndUpdateExperience(playerId, request.getPoints());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            log.error("Get error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
