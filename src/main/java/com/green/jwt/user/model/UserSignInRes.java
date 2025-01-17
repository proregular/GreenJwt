package com.green.jwt.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Schema(title = "로그인 응답")
public class UserSignInRes {
    private long id;
    private String name;
    private String accessToken;
}
