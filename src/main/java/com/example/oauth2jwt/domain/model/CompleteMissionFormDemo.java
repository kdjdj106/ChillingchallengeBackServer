package com.example.oauth2jwt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@Builder
public class CompleteMissionFormDemo {
    private String usercode;

    // 미션1  (단순 텍스트)
    private String mission1title;
    private String mission1subTitle;
    private String mission1comment1;
    private String mission1comment2;
    // 미션2  (복합 텍스트)
    private String mission2title;
    private String mission2subTitle;
    private String mission2comment1;
    private String mission2comment2;
    private String mission2comment3;
    private String mission2comment4;
    // 미션3  (사진, 텍스트)
    private String mission3title;
    private String mission3subTitle;
    private String mission3comment1;
    private String mission3comment2;



}
