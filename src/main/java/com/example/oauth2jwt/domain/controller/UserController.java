package com.example.oauth2jwt.domain.controller;

import com.example.oauth2jwt.domain.dto.UserInfoDto;
import com.example.oauth2jwt.domain.dto.UserSignUpDto;
import com.example.oauth2jwt.domain.model.CompleteMissionToBackForm;
import com.example.oauth2jwt.domain.model.UserInfoForm;
import com.example.oauth2jwt.domain.service.MissionService;
import com.example.oauth2jwt.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MissionService missionService;

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


    @PostMapping("/showMyHistory")
    public ResponseEntity<?>  showMyHistory(@RequestParam(value = "code") String usercode){
        return ResponseEntity.ok(userService.showMyHistory(usercode));
    }

    @PostMapping("/showMyInfo")
    public ResponseEntity<UserInfoForm.Response>  showMyInfo(@RequestParam(value = "code") String usercode){
        UserInfoDto infoDto = userService.showMyInfo(usercode);

        return ResponseEntity.ok(new UserInfoForm.Response(
                infoDto.getNickname(),
                infoDto.getMissionCnt(),
                infoDto.getAttendance(),
                infoDto.getContinuous()
        ));
    }

    @PostMapping("/test")
    public String test(@RequestBody List<CompleteMissionToBackForm> list){
        List<CompleteMissionToBackForm> testlist = list;
        return "ok";
    }
}
