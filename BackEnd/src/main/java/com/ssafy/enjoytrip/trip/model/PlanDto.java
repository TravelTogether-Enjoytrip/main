package com.ssafy.enjoytrip.trip.model;

import java.util.List;

public class PlanDto {

    private int groupId;
    private String userId;
    private List<List<Integer>> plan;
    
    

	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<List<Integer>> getPlan() {
		return plan;
	}
	public void setPlan(List<List<Integer>> plan) {
		this.plan = plan;
	}
}
