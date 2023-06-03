package com.example.oauth2jwt.domain.service;

import com.example.oauth2jwt.domain.dto.UserSignUpDto;
import com.example.oauth2jwt.domain.entity.User;
import com.example.oauth2jwt.domain.repository.UserRepository;
import com.example.oauth2jwt.domain.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private String pwd = null;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }
        pwd = passwordEncoder.encode(userSignUpDto.getPassword());
        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(pwd)
                .nickname(userSignUpDto.getNickname())
                .role(Role.USER)
                .build();


        userRepository.save(user);
    }
}
