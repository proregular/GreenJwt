package com.green.jwt.user;

import com.green.jwt.user.model.UserSignUpReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserSignUpReq req);
    int insUserRole(UserSignUpReq req);
}
