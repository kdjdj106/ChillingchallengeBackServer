package com.example.oauth2jwt.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "BoardMission2")
@AllArgsConstructor
public class BoardMission2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    private Integer missionId;
    private String comment1;
    private String comment2;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "usercode")
    private User user;




}
