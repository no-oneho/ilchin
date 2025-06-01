package org.groupware.ilchin.controller;

import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.dto.Response;
import org.groupware.ilchin.dto.user.request.LoginReq;
import org.groupware.ilchin.dto.user.request.SignUp;
import org.groupware.ilchin.dto.user.response.LoginResp;
import org.groupware.ilchin.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
