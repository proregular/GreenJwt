package com.green.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplication.class, args);
    }
    /*
        - 로그인: AT, RT 생성, AT는 BODY로 리턴(응답), RT는 Cookie에 담아서 리턴(응답)
        - 프론트는 AT를 받은 순간부터 모든 요청의 HEADER에 Authorization 키값으로 "Bearer ${AT}"를 보내준다.
        - 요청이 들어올 때마다 AT 체크를 한다. 현 프로젝트 기준 TokenAuthenticationFilter에서 담당.
            : HEADER에 Authorization키가 있는지 확인, 있으면 Bearer를 뺀 AT를 뽑아낸다.
                >> Authentication 객체를 생성하고 SecurityContextHolder에 담는다. (Spring Framework Security(SFS) 미들웨어 쓰는데
                                                                                SFS가 인증처리하는 방법
                                                                                , 즉 Authentication객체가
                                                                                SecurityContextHolder에 담겨있어야 인증되었다고 처리한다.)
            : HEADER에 Authhrization키가 없었다면 아무런 작업을 안 한다. SFS가
    */

}
