package com.example.oauth2jwt.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowFeedToFrontForm {

    private Integer missionId;
    private LocalDateTime localDateTime;
    private String uuid;
    private List<String> stringAndPath;

}
