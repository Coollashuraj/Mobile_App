package com.appdev.app.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.appdev.app.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	UserDto CreateUser(UserDto user);
	UserDto GetUser(String email);
	UserDto getUserByUserId(String userid);
	UserDto UpdateUser(String id,UserDto userDto);
    void deleteUser(String UserId);
	List<UserDto> getUsers(int page, int limit);
}
