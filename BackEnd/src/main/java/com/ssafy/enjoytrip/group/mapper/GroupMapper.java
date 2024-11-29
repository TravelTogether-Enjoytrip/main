package com.ssafy.enjoytrip.group.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.group.model.GroupDto;
import com.ssafy.enjoytrip.user.model.UserDto;

@Mapper
public interface GroupMapper {
	void makeGroup(GroupDto groupDto) throws SQLException;
	List<GroupDto> getGroupList(String userId) throws SQLException;
	List<UserDto> getGroupMemberList(String groupId) throws SQLException;
//	void addUserIntoGroup(String userId, int groupId) throws SQLException;
	void modifyGroup(GroupDto groupDto);
}
