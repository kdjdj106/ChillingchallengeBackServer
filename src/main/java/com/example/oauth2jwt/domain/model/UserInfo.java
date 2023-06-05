package com.example.oauth2jwt.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserInfo {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String message;
    }
}
