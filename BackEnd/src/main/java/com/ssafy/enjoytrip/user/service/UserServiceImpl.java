package com.ssafy.enjoytrip.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.user.model.UserDto;
import com.ssafy.enjoytrip.user.mapper.UserMapper;


@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;

	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}

//	@Override
//	public int idCheck(String userId) throws Exception {
//		// TODO Auto-generated method stub
//		return userMapper.idCheck(userId);
//	}

	@Override
	public void joinUser(UserDto userDto) throws Exception {
		// TODO Auto-generated method stub
		userMapper.joinUser(userDto);

	}

	@Override
	public UserDto loginUser(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		
		return userMapper.loginUser(map);
	}

	@Override
	public List<UserDto> listUser() throws Exception {
		// TODO Auto-generated method stub
		return userMapper.listUser();
	}

	@Override
	public UserDto getUser(String userId) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.getUser(userId);
	}

	@Override
	public void updateUser(UserDto userDto) throws Exception {
		// TODO Auto-generated method stub
		userMapper.updateUser(userDto);

	}

	@Override
	public void deleteUser(String userId) throws Exception {
		// TODO Auto-generated method stub
		userMapper.deleteUser(userId);	
		
	}

	@Override
	public UserDto login(UserDto userDto) throws Exception {
		return userMapper.login(userDto);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		userMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public void deleteRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		userMapper.deleteRefreshToken(map);
	}

	@Override
	public UserDto userInfo(String userId) throws Exception {
		return userMapper.userInfo(userId);
	}

	@Override
	public void modifyUser(UserDto userDto) throws Exception {
		System.out.println(userDto);
		userMapper.modifyUser(userDto);
	}

	@Override
	public List<Integer> getTour(String userId) {
		return userMapper.getTour(userId);
	}


}
