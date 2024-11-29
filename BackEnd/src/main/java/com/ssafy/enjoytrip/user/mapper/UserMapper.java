package com.ssafy.enjoytrip.user.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.user.model.UserDto;


@Mapper
public interface UserMapper {
//	int idCheck(String userId) throws SQLException;
	void joinUser(UserDto userDto) throws SQLException;
	UserDto loginUser(Map<String, String> map) throws SQLException;
	UserDto login(UserDto userDto) throws Exception;
	UserDto userInfo(String userId) throws Exception;
	void modifyUser(UserDto userDto);
	
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
	/* Admin */
	List<UserDto> listUser() throws SQLException;
	UserDto getUser(String userId) throws SQLException;
	void updateUser(UserDto userDto) throws SQLException;
	void deleteUser(String userId) throws SQLException;
	List<Integer> getTour(String userId);
}


