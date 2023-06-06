package com.example.oauth2jwt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
@Builder
public class CompleteMissionToBackForm {
    private String usercode;
    private Integer missionType;
    private Integer missionId;
    private List<String> stringAndPath;
    // 미션1  (단순 텍스트)


}
