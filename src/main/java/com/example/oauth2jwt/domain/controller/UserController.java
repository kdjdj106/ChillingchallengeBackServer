package com.example.oauth2jwt.domain.controller;

import com.example.oauth2jwt.domain.dto.UserInfoDto;
import com.example.oauth2jwt.domain.dto.UserSignUpDto;
import com.example.oauth2jwt.domain.model.CompleteMissionForm;
import com.example.oauth2jwt.domain.model.CompleteMissionFormDemo;
import com.example.oauth2jwt.domain.model.UserInfoForm;
import com.example.oauth2jwt.domain.service.MissionService;
import com.example.oauth2jwt.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> completeMission(@RequestBody CompleteMissionForm form) throws Exception {

        return ResponseEntity.ok( missionService.completeMission(form));
    }
    @PostMapping("/completeMissionDemo")
    public ResponseEntity<?> completeMission(@ModelAttribute CompleteMissionFormDemo demo, @RequestPart MultipartFile image) throws Exception {
        CompleteMissionFormDemo demo1 = demo;
        return ResponseEntity.ok( missionService.completeMissionDemo(image, demo));
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
}
