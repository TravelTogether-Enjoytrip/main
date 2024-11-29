package com.ssafy.enjoytrip.group.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.group.mapper.GroupMapper;
import com.ssafy.enjoytrip.group.model.GroupDto;
import com.ssafy.enjoytrip.user.model.UserDto;

@Service
public class GroupServiceImpl implements GroupService {
	
	private final GroupMapper groupMapper;
	
	public GroupServiceImpl(GroupMapper groupMapper) {
		super();
		this.groupMapper = groupMapper;
	}

	@Override
	public void makeGroup(GroupDto groupDto) throws Exception {
		// 이 과정이 중간에 끊기면 안되기 때문에 atomic하게 만들어줘야 한다.
		groupMapper.makeGroup(groupDto);
	}
	
	@Override
	public List<GroupDto> getGroupList(String userId) throws Exception {
		return groupMapper.getGroupList(userId);
	}

	@Override
	public List<UserDto> getGroupMemberList(String groupId) throws Exception {
		return groupMapper.getGroupMemberList(groupId);
	}

	@Override
	public void modifyGroup(GroupDto groupDto) {
		groupMapper.modifyGroup(groupDto);
	}
	
	

}
