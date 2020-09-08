package com.platform.cloud.xpmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EXPERIENCE_LOG")
public class ExperienceLog {

    @Id
    @Column(name = "EXPERIENCE_LOG_ID")
    private String experienceLogId;

    @Column(name = "EXPERIENCE_ID")
    private int experienceId;

    @Column(name = "PLAYER_ID")
    private int playerId;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "TYPE", length = 9)
    private String type;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "CREATED_AT_TIMESTAMP")
    private Timestamp createdAtTimestamp;
}
