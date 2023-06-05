package com.example.oauth2jwt.domain.service;

import com.example.oauth2jwt.domain.dto.UserInfoDto;
import com.example.oauth2jwt.domain.dto.UserSignUpDto;
import com.example.oauth2jwt.domain.entity.BoardMission1;
import com.example.oauth2jwt.domain.entity.BoardMission2;
import com.example.oauth2jwt.domain.entity.BoardMission3;
import com.example.oauth2jwt.domain.entity.User;
import com.example.oauth2jwt.domain.model.CompleteMissionForm;
import com.example.oauth2jwt.domain.model.ShowHistoryForm;
import com.example.oauth2jwt.domain.model.UserInfoForm;
import com.example.oauth2jwt.domain.repository.BoardMission1Repository;
import com.example.oauth2jwt.domain.repository.BoardMission2Repository;
import com.example.oauth2jwt.domain.repository.BoardMission3Repository;
import com.example.oauth2jwt.domain.repository.UserRepository;
import com.example.oauth2jwt.domain.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardMission1Repository boardMission1Repository;
    private final BoardMission2Repository boardMission2Repository;
    private final BoardMission3Repository boardMission3Repository;
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
    // 유저 정보 전달
    public UserInfoDto showMyInfo(String usercode){
        User user = userRepository.findByUsercode(usercode).get();
        UserInfoDto infoDto = UserInfoDto.fromEntity(user);

        return infoDto;
    }

    // 유저 프로필 이미지 변경 필요


    // 유저 히스토리(피드) 정보 추출
    public List<ShowHistoryForm> showMyHistory(String usercode){
        ShowHistoryForm form = null;

        List<ShowHistoryForm> myHistory = new ArrayList<>();
        User user = userRepository.findByUsercode(usercode).get();


        List<BoardMission1> list1 = new ArrayList<>();
        list1 = boardMission1Repository.findByUser(user);
        if (list1 != null){
            for (BoardMission1 boardMission1 : list1) {
                form.setMissionNum(1);
                form.setCreatedDt(boardMission1.getCreatedDt());
                form.setMission1title(boardMission1.getTitle());
                form.setMission1comment1(boardMission1.getComment1());
                form.setMission1comment2(boardMission1.getComment2());
                form.setMission1subTitle(boardMission1.getSubTitle());

                myHistory.add(form);
            }

        }


        List<BoardMission2> list2 = new ArrayList<>();
        list2 = boardMission2Repository.findByUser(user);
       if (list2 != null){
           for (BoardMission2 boardMission2 : list2){
               form.setMissionNum(2);
               form.setCreatedDt(boardMission2.getCreatedDt());
               form.setMission2title(boardMission2.getTitle());
               form.setMission2subTitle(boardMission2.getSubTitle());
               form.setMission2comment1(boardMission2.getComment1());
               form.setMission2comment2(boardMission2.getComment2());
               form.setMission2comment3(boardMission2.getComment3());
               form.setMission2comment4(boardMission2.getComment4());

               myHistory.add(form);
           }
       }

        List<BoardMission3> list3 = new ArrayList<>();
        list3 = boardMission3Repository.findByUser(user);
        if (list3 != null){
            for (BoardMission3 boardMission3 : list3){
                form.setMissionNum(2);
                form.setCreatedDt(boardMission3.getCreatedDt());
                form.setMission3title(boardMission3.getTitle());
                form.setMission3subTitle(boardMission3.getSubTitle());
                form.setMission3comment1(boardMission3.getComment1());
                form.setMission3comment2(boardMission3.getComment2());
                form.setMission3filename(boardMission3.getFilename());
                form.setMission3filepath(boardMission3.getFilepath());

                myHistory.add(form);
            }
        }

        return myHistory;
    }
}
