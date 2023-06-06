package com.example.oauth2jwt.domain.service;

import com.example.oauth2jwt.domain.dto.UserInfoDto;
import com.example.oauth2jwt.domain.dto.UserSignUpDto;
import com.example.oauth2jwt.domain.entity.BoardMission1;
import com.example.oauth2jwt.domain.entity.BoardMission2;
import com.example.oauth2jwt.domain.entity.BoardMission3;
import com.example.oauth2jwt.domain.entity.User;
import com.example.oauth2jwt.domain.model.ShowFeedToFrontForm;
import com.example.oauth2jwt.domain.repository.BoardMission1Repository;
import com.example.oauth2jwt.domain.repository.BoardMission2Repository;
import com.example.oauth2jwt.domain.repository.BoardMission3Repository;
import com.example.oauth2jwt.domain.repository.UserRepository;
import com.example.oauth2jwt.domain.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
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

//    public String getUserCode(){
//
//    }
    // 유저 프로필 이미지 변경 필요


    // 유저 히스토리(피드) 정보 추출
    public List<ShowFeedToFrontForm> showMyHistory(String usercode){


        List<ShowFeedToFrontForm> myHistory = new ArrayList<>();
        User user = userRepository.findByUsercode(usercode).get();


        List<BoardMission1> list1 = new ArrayList<>();
        list1 = boardMission1Repository.findByUser(user);
        if (list1 != null){

            for (int i = 0; i < list1.size(); i++) {
                ShowFeedToFrontForm form = new ShowFeedToFrontForm();
                form.setMissionId(list1.get(i).getMissionId());
                List<String> stringAndPath = new ArrayList<>();
                stringAndPath.add(list1.get(i).getImagePath());
                stringAndPath.add(list1.get(i).getComment1());
                stringAndPath.add(list1.get(i).getComment2());
                form.setUuid(list1.get(i).getUuid());
                form.setStringAndPath(stringAndPath);
                form.setLocalDateTime(list1.get(i).getCreatedDt());

                myHistory.add(form);
            }

        }


        List<BoardMission2> list2 = new ArrayList<>();
        list2 = boardMission2Repository.findByUser(user);
        if (list2 != null){

            for (int i = 0; i < list2.size(); i++) {
                ShowFeedToFrontForm form = new ShowFeedToFrontForm();
                form.setMissionId(list2.get(i).getMissionId());
                List<String> stringAndPath = new ArrayList<>();
                stringAndPath.add(list2.get(i).getComment1());
                stringAndPath.add(list2.get(i).getComment2());
                form.setUuid(list2.get(i).getUuid());
                form.setStringAndPath(stringAndPath);
                form.setLocalDateTime(list2.get(i).getCreatedDt());

                myHistory.add(form);
            }

        }

        List<BoardMission3> list3 = new ArrayList<>();
        list3 = boardMission3Repository.findByUser(user);
        if (list3 != null){

            for (int i = 0; i < list3.size(); i++) {
                ShowFeedToFrontForm form = new ShowFeedToFrontForm();
                form.setMissionId(list3.get(i).getMissionId());
                List<String> stringAndPath = new ArrayList<>();
                stringAndPath.add(list3.get(i).getComment1());
                stringAndPath.add(list3.get(i).getComment2());
                stringAndPath.add(list3.get(i).getComment3());
                stringAndPath.add(list3.get(i).getComment4());
                form.setUuid(list3.get(i).getUuid());
                form.setStringAndPath(stringAndPath);
                form.setLocalDateTime(list3.get(i).getCreatedDt());

                myHistory.add(form);
            }

        }

        myHistory.sort(Comparator.comparing(ShowFeedToFrontForm::getLocalDateTime));

        return myHistory;
    }
}
