package com.example.oauth2jwt.domain.repository;

import com.example.oauth2jwt.domain.entity.User;
import com.example.oauth2jwt.domain.type.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByRefreshToken(String refreshToken);



    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<User> findByUsercode(String usercode);
}
