package com.green.jwt.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.jwt.config.jwt.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class UserSignUpReq {
    @JsonIgnore
    @Setter
    private long id;
    private final String email;
    @Setter
    private String pw;
    private final String name;
    private final List<UserRole> roles = new ArrayList<>();
}
