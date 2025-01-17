package com.green.jwt.user.model;

import com.green.jwt.config.jwt.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserSelOne {
    private long id;
    private String email;
    private String pw;
    private String name;
    private List<UserRole> roles;
}
