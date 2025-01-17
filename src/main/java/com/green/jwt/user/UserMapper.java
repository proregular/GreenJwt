package com.green.jwt.user;

import com.green.jwt.user.model.UserSelOne;
import com.green.jwt.user.model.UserSignInReq;
import com.green.jwt.user.model.UserSignUpReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    int insUser(UserSignUpReq req);
    int insUserRole(UserSignUpReq req);
    Optional<UserSelOne> selUserWithRoles(UserSignInReq req);
}
