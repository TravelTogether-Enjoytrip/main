package com.ssafy.enjoytrip.group.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.group.model.GroupDto;
import com.ssafy.enjoytrip.group.service.GroupService;
import com.ssafy.enjoytrip.user.model.UserDto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.java.Log;

@RestController
@RequestMapping("/groupapi")
@CrossOrigin("*")
public class GroupController {
	
	private final GroupService groupService;
	
	public GroupController(GroupService groupService) {
		super();
		this.groupService = groupService;
	}

	@PostMapping("/makegroup")
	public ResponseEntity<?> makeGroup(@RequestBody GroupDto groupDto){
		try {
			groupService.makeGroup(groupDto);
			return new ResponseEntity<GroupDto>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/modifygroup/{groupId}")
	public ResponseEntity<?> modifyGroup(@PathVariable ("groupId") int groupId, @RequestBody GroupDto groupDto){
		groupDto.setGroupId(groupId);
		try {
			groupService.modifyGroup(groupDto);
			return new ResponseEntity<GroupDto>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/list/{userId}")
	public ResponseEntity<?> getGroupList(@PathVariable ("userId") @Parameter(description = "해당 유저가 속한 모든 그룹들을 가져온다.", required = true) String userId){
		List<GroupDto> list = new ArrayList<>();
		try {
			list = groupService.getGroupList(userId);
			return new ResponseEntity<List>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/memberlist/{groupId}")
	public ResponseEntity<?> getGroupMemberList(@PathVariable ("groupId") @Parameter(description ="이 그룹의 멤버들을 전부 가져온다.", required = true) String groupId)
	{
		List<UserDto> list = new ArrayList<>();
		try {
			list = groupService.getGroupMemberList(groupId);
			return new ResponseEntity<List>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
}
