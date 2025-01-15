package com.green.jwt.user;

import com.green.jwt.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(UserSignUpReq req) {
        String hashedPw = passwordEncoder.encode(req.getPw());
        req.setPw(hashedPw);
        userMapper.insUser(req);
        userMapper.insUserRole(req);
    }
}
