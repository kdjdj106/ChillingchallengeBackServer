package com.example.oauth2jwt.domain.service;

import com.example.oauth2jwt.domain.entity.BoardMission1;
import com.example.oauth2jwt.domain.entity.BoardMission2;
import com.example.oauth2jwt.domain.entity.BoardMission3;
import com.example.oauth2jwt.domain.entity.User;
import com.example.oauth2jwt.domain.model.CompleteMissionToBackForm;
import com.example.oauth2jwt.domain.repository.BoardMission1Repository;
import com.example.oauth2jwt.domain.repository.BoardMission2Repository;
import com.example.oauth2jwt.domain.repository.BoardMission3Repository;
import com.example.oauth2jwt.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MissionService {
    private final UserRepository userRepository;
    private final BoardMission3Repository boardMission3Repository;
    private final BoardMission2Repository boardMission2Repository;
    private final BoardMission1Repository boardMission1Repository;

    LocalDate today = LocalDate.now();
    int todayNum = today.getDayOfMonth();

    String lastAttendance = Integer.toString(todayNum);


    public User completeMission(List<CompleteMissionToBackForm> list) throws Exception {
        Long usercode = list.get(0).getUsercode();
        Optional<User> optionalUser = userRepository.findById(usercode);
        // 마지막 출석일자와 오늘을 비교하여연속 출석일수 증가 or 초기화



        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            for (int i = 0; i < list.size(); i++) {
                switch (list.get(i).getMissionType()){
                    case 1:
                        writeBoardMission1(list.get(i).getStringAndPath(), user, list.get(i).getMissionId());
                        break;
                    case 2:
                        writeBoardMission2(list.get(i).getStringAndPath(), user, list.get(i).getMissionId());
                        break;
                    case 3:
                        writeBoardMission3(list.get(i).getStringAndPath(), user, list.get(i).getMissionId());
                        break;
                }

            }

            user.setTodayCheck(true);
            user.setMissionCnt(user.getMissionCnt()+1);

            // 마지막 출석일자와 오늘의 달을 비교하여 다르면 출석일자 초기화
            if (user.getLastAttendance() == null){
                user.setAttendance(lastAttendance);
                user.setContinuous(1);
            }else {
                if (user.getLastAttendance().getDayOfMonth() != today.getDayOfMonth()){
                    user.setAttendance(lastAttendance);

                }else {
                    //같다면 공백 과 오늘 날자 추가
                    if(user.getLastAttendance() == null){
                        user.setAttendance(user.getAttendance()+" "+lastAttendance);
                    }

                }

                // 마지막 출석 일자와 오늘의 일자를 비교하여 연속일수 초기화 및 추가
                if (user.getLastAttendance().plusDays(1L).equals(today)){
                    user.setContinuous(user.getContinuous()+1);
                }else {
                    user.setContinuous(1);
                }
            }
            user.setLastAttendance(today);
            userRepository.save(user);

        }else {
            log.info("해당 유저코드에 대한 유저를 찾을수 없습니다.");
        }

        return optionalUser.get();
    }


    private void writeBoardMission3(List<String> getStringAndPath, User user, Integer missionId) throws Exception {



        BoardMission3 boardMission3 = BoardMission3.builder()
                .missionId(missionId)
                .comment1(getStringAndPath.get(0).toString())
                .comment2(getStringAndPath.get(1).toString())
                .comment3(getStringAndPath.get(2).toString())
                .comment4(getStringAndPath.get(3).toString())
                .user(user)
                .uuid(UUID.randomUUID().toString())
                .createdDt(LocalDateTime.now())
                .build();

        boardMission3Repository.save(boardMission3);
    }

    private void writeBoardMission2(List<String> getStringAndPath, User user, Integer missionId){
        BoardMission2 boardMission2 = BoardMission2.builder()
                .missionId(missionId)
                .comment1(getStringAndPath.get(0).toString())
                .comment2(getStringAndPath.get(1).toString())
                .user(user)
                .uuid(UUID.randomUUID().toString())
                .createdDt(LocalDateTime.now())
                .build();

        boardMission2Repository.save(boardMission2);
    }
    private void writeBoardMission1(List<String> getStringAndPath, User user, Integer missionId){
        BoardMission1 boardMission1 = BoardMission1.builder()
                .missionId(missionId)
                .imagePath(getStringAndPath.get(0).toString())
                .comment1(getStringAndPath.get(1).toString())
                .comment2(getStringAndPath.get(2).toString())
                .user(user)
                .uuid(UUID.randomUUID().toString())
                .createdDt(LocalDateTime.now())
                .build();

        boardMission1Repository.save(boardMission1);
    }

//    public User completeMissionDemo(MultipartFile file, CompleteMissionFormDemo form) throws Exception {
//        log.info("데모 시작");
//        Optional<User> optionalUser = userRepository.findByUsercode(form.getUsercode());
//        // 마지막 출석일자와 오늘을 비교하여연속 출석일수 증가 or 초기화
//
//
//
//        if (optionalUser.isPresent()){
//            User user = optionalUser.get();
//            writeBoardMission3Demo(file, form, user);
//            writeBoardMission2Demo(form, user);
//            writeBoardMission1Demo(form, user);
//            user.setTodayCheck(true);
//            user.setMissionCnt(user.getMissionCnt()+1);
//            if (user.getLastAttendance() == null){
//                user.setAttendance(lastAttendance);
//                user.setContinuous(1);
//            }else {
//                if (user.getLastAttendance().getDayOfMonth() != today.getDayOfMonth()){
//                    user.setAttendance(lastAttendance);
//
//                }else {
//                    //같다면 공백 과 오늘 날자 추가
//                    user.setAttendance(user.getAttendance()+" "+lastAttendance);
//                }
//
//                // 마지막 출석 일자와 오늘의 일자를 비교하여 연속일수 초기화 및 추가
//                if (user.getLastAttendance().plusDays(1L).equals(today)){
//                    user.setContinuous(user.getContinuous()+1);
//                }else {
//                    user.setContinuous(1);
//                }
//            }
//            // 마지막 출석일자와 오늘의 달을 비교하여 다르면 출석일자 초기화
//
//            user.setLastAttendance(today);
//            userRepository.save(user);
//        }else {
//            log.info("해당 유저코드에 대한 유저를 찾을수 없습니다.");
//        }
//
//        return optionalUser.get();
//    }
//
//    private void writeBoardMission3Demo(MultipartFile file, CompleteMissionFormDemo form, User user) throws Exception {
//        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files" ;
//
//
//        UUID uuid = UUID.randomUUID();
//        String fileName = uuid+ "_"+ file.getOriginalFilename();
//        File saveFile = new File(projectPath, fileName);
//
//        file.transferTo(saveFile);
//
//        BoardMission3 boardMission3 = BoardMission3.builder()
//                .title(form.getMission1title())
//                .subTitle(form.getMission1subTitle())
//                .comment1(form.getMission1comment1())
//                .comment2(form.getMission1comment2())
//                .filename(fileName)
//                .filepath(projectPath)
//                .user(user)
//                .createdDt(LocalDateTime.now())
//                .build();
//
//        boardMission3Repository.save(boardMission3);
//    }
//
//    private void writeBoardMission2Demo(CompleteMissionFormDemo form, User user){
//        BoardMission2 boardMission2 = BoardMission2.builder()
//                .title(form.getMission2title())
//                .subTitle(form.getMission2subTitle())
//                .comment1(form.getMission2comment1())
//                .comment2(form.getMission2comment2())
//                .comment3(form.getMission2comment3())
//                .comment4(form.getMission2comment4())
//                .user(user)
//                .createdDt(LocalDateTime.now())
//                .build();
//
//        boardMission2Repository.save(boardMission2);
//    }
//    private void writeBoardMission1Demo(CompleteMissionFormDemo form, User user){
//        BoardMission1 boardMission1 = BoardMission1.builder()
//                .title(form.getMission1title())
//                .subTitle(form.getMission1subTitle())
//                .comment1(form.getMission1comment1())
//                .comment2(form.getMission1comment2())
//                .user(user)
//                .createdDt(LocalDateTime.now())
//                .build();
//        boardMission1Repository.save(boardMission1);
//    }
}
