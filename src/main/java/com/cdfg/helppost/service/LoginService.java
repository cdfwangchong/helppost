package com.cdfg.helppost.service;

import com.cdfg.helppost.pojo.dto.LeavedDto;
import com.cdfg.helppost.pojo.dto.UserDto;
import com.cdfg.helppost.pojo.until.UserEntity;

import java.util.Map;

public interface LoginService {
    Map<String, Object> login(UserDto userDto);

    UserEntity qryUser(LeavedDto leavedDto);
}
