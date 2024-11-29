package com.ssafy.enjoytrip.user.service;

import java.util.List;
import java.util.Map;

import com.ssafy.enjoytrip.user.model.UserDto;

public interface UserService {
//	int idCheck(String userId) throws Exception;
	void joinUser(UserDto userDto) throws Exception;
	UserDto loginUser(Map<String, String> map) throws Exception;
	void modifyUser(UserDto userDto) throws Exception;
	
	UserDto login(UserDto userDto) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleteRefreshToken(String userId) throws Exception;
	
	/* Admin */
	List<UserDto> listUser() throws Exception;
	UserDto getUser(String userId) throws Exception;
	void updateUser(UserDto userDto) throws Exception;
	void deleteUser(String userid) throws Exception;
	List<Integer> getTour(String userId);
}
