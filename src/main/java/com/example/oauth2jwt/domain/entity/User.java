package com.example.oauth2jwt.domain.entity;

import com.example.oauth2jwt.domain.type.Role;
import com.example.oauth2jwt.domain.type.SocialType;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "USER")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    private String email; // 이메일
    private String password; // 비밀번호
    private String nickname; // 닉네임
    private String imageUrl; // 프로필 이미지
    private String usercode;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // KAKAO, NAVER, GOOGLE

    private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)
    private String refreshToken; // 리프레시 토큰



    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

}
