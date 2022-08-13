package com.jingdianjichi.user.service;

import com.jingdianjichi.user.entity.dto.UserDto;

public interface UserService {

    int addUser(UserDto userDto);

    int delete(Integer id);

}
