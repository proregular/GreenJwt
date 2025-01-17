package com.green.jwt.user;

import com.green.jwt.user.model.UserSignInReq;
import com.green.jwt.user.model.UserSignInRes;
import com.green.jwt.user.model.UserSignUpReq;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("sign-up")
    public long signUp(@RequestBody UserSignUpReq req) {
        log.info("sign-up request: {}", req);
        userService.signUp(req);

        return req.getId();
    }

    @PostMapping("sign-in")
    public UserSignInRes signIn(@RequestBody UserSignInReq req, HttpServletResponse response) {
        log.info("sign in request: {}", req);

        return userService.signIn(req, response);
    }

    @GetMapping
    public String get() {
        return "user";
    }
}
