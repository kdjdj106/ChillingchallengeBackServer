package com.example.oauth2jwt.domain.entity;

import com.example.oauth2jwt.domain.model.CompleteMissionForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "BoardMission3")
@AllArgsConstructor
public class BoardMission3 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardId")
    private Long id;

    private String title;
    private String subTitle;
    private String comment1;
    private String comment2;

    private String filename;
    private String filepath;

    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    @ManyToOne
    @JoinColumn(name = "usercode")
    private User user;


}
