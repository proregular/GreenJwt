package com.green.jwt;

import com.green.jwt.config.JwtConst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtApplicationTests {

    @Autowired
    private JwtConst jwtConst;

    @Test
    void objJwtConst() {
        System.out.println(jwtConst);
    }

}
