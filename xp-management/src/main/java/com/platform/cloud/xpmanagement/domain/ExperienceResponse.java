package com.platform.cloud.xpmanagement.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExperienceResponse {
    @JsonProperty("balance")
    private int balance;
    @JsonProperty("created_at_timestamp")
    private String createdAtTimestamp;
    @JsonProperty("updated_at_timestamp")
    private String updatedAtTimestamp;
}
