package com.ssafy.enjoytrip.group.model;

import java.util.List;

import com.ssafy.enjoytrip.user.model.UserDto;

public class GroupDto {
	private int GroupId;
	private String GroupName;
	private List<String> groupMembers;
	
	
	public int getGroupId() {
		return GroupId;
	}
	public void setGroupId(int groupId) {
		GroupId = groupId;
	}
	public String getGroupName() {
		return GroupName;
	}
	public void setGroupName(String groupName) {
		GroupName = groupName;
	}
	public List<String> getGroupMembers() {
		return groupMembers;
	}
	public void setGroupMembers(List<String> groupMembers) {
		this.groupMembers = groupMembers;
	}
	
}
