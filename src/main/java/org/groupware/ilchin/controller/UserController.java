package org.groupware.ilchin.controller;

import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.dto.user.request.LoginReq;
import org.groupware.ilchin.dto.user.request.PatchPasswordReq;
import org.groupware.ilchin.dto.user.request.PatchUserReq;
import org.groupware.ilchin.dto.user.request.SignUp;
import org.groupware.ilchin.dto.user.response.LoginResp;
import org.groupware.ilchin.dto.user.response.UserProfileResp;
import org.groupware.ilchin.security.Auth;
import org.groupware.ilchin.service.UserService;
import org.springframework.web.bind.annotation.*;
import utils.Api;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public Response<LoginResp> signUp(@RequestBody SignUp signUp) {
        return Api.success(200, "success", userService.createUser(signUp));
    }

    @PostMapping("/login")
    public Response<LoginResp> login(@RequestBody LoginReq loginReq) {
        return Api.success(200, "success", userService.login(loginReq));
    }

    @Auth
    @GetMapping
    public Response<UserProfileResp> getCurrentUserProfile() {
        return Api.success(200, "내 정보 조회 완료", userService.getCurrentUserProfile());
    }

    @Auth
    @PatchMapping
    public Response<UserProfileResp> patchCurrentUserProfile(@RequestBody PatchUserReq patchUserReq) {
        return Api.success(200, "내 정보 수정 완료", userService.patchCurrentUserProfile(patchUserReq));
    }

    @Auth
    @GetMapping("{id}")
    public Response<UserProfileResp> getTargetUserProfile(@PathVariable Long id) {
        return Api.success(200, "정보 조회 완료", userService.getTargetUserProfile(id));
    }

    @Auth
    @PatchMapping("password")
    public Response<String> patchCurrentUserPassword(@RequestBody PatchPasswordReq patchPasswordReq) {
        return Api.success(200, "패스워드 변경 완료", userService.patchCurrentUserPassword(patchPasswordReq));

    }

}
