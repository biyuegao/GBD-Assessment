package com.platform.cloud.xpmanagement.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "EXPERIENCE")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "EXPERIENCE_ID")
    private int experienceId;

    @Column(name = "PLAYER_ID")
    private int playerId;

    @Column(name = "BALANCE")
    private int balance;

    @Column(name = "CREATED_AT_TIMESTAMP")
    private Timestamp createdAtTimestamp;

    @Column(name = "UPDATED_AT_TIMESTAMP")
    private Timestamp updatedAtTimestamp;
}
