package com.example.oauth2jwt.domain.controller;

import com.example.oauth2jwt.domain.dto.UserInfoDto;
import com.example.oauth2jwt.domain.dto.UserSignUpDto;import com.example.oauth2jwt.domain.entity.User;
import com.example.oauth2jwt.domain.model.*;
import com.example.oauth2jwt.domain.service.MissionService;
import com.example.oauth2jwt.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MissionService missionService;
    private  List<ShowFeedToFrontForm> myHistory;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @PostMapping("/completeMission")
    public ResponseEntity<?> completeMission(@RequestBody List<CompleteMissionToBackForm> list) throws Exception {

        return ResponseEntity.ok( missionService.completeMission(list));
    }


    @GetMapping("/showMyHistory")
    public ResponseEntity<List<ShowFeedToFrontForm>> getHistoryPage(@RequestParam(value = "code") Long usercode,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        myHistory = userService.getMyHistory(usercode);

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, myHistory.size());

        if (startIndex > endIndex) {
            // 페이지 범위가 데이터 크기를 초과하는 경우 빈 결과 반환
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<ShowFeedToFrontForm> pageData = myHistory.subList(startIndex, endIndex);
        return ResponseEntity.ok(pageData);
    }

    @GetMapping("/showMyInfo")
    public ResponseEntity<UserInfoForm.Response>  showMyInfo(@RequestParam(value = "code") Long usercode){
        UserInfoDto infoDto = userService.showMyInfo(usercode);

        return ResponseEntity.ok(new UserInfoForm.Response(
                infoDto.getNickname(),
                infoDto.getMissionCnt(),
                infoDto.getAttendance(),
                infoDto.getContinuous(),
                infoDto.getProfileImagePath()
        ));
    }

    @PostMapping("/test")
    public String test(@RequestBody List<CompleteMissionToBackForm> list){
        List<CompleteMissionToBackForm> testlist = list;
        return "ok";
    }

    @PutMapping("/update/nickname/{usercode}")
    public ResponseEntity<User> updateUserNickname(@PathVariable Long usercode,
                                           @RequestBody UserNicknameUpdateRequest request) {

        return ResponseEntity.ok(userService.updateUserNickname(usercode,request));
    }
    @PutMapping("/update/imageUrl/{usercode}")
    public ResponseEntity<User> updateUserImageUrl(@PathVariable Long usercode,
                                                   @RequestBody UserImageUrlUpdateRequest request) {

        return ResponseEntity.ok(userService.updateUserImageUrl(usercode,request));
    }
}
