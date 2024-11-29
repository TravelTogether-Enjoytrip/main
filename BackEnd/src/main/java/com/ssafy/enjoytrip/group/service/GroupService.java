package com.ssafy.enjoytrip.group.service;


import java.util.List;

import com.ssafy.enjoytrip.group.model.GroupDto;
import com.ssafy.enjoytrip.user.model.UserDto;


public interface GroupService {
	void makeGroup(GroupDto groupDto) throws Exception;
	List<GroupDto> getGroupList(String userId) throws Exception;
	List<UserDto> getGroupMemberList(String groupId) throws Exception;
	void modifyGroup(GroupDto groupDto);
}
