package com.ssafy.enjoytrip.trip.model;

public class SearchRequestDto {
	
	private int areaCode;
	private int gugun;
	private int contentTypeId;
	private String keyword;
	public SearchRequestDto() {
		super();
	}
	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}
	public void setGugun(int gugun) {
		this.gugun = gugun;
	}
	public void setContentTypeId(int contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getAreaCode() {
		return areaCode;
	}
	public int getGugun() {
		return gugun;
	}
	public int getContentTypeId() {
		return contentTypeId;
	}
	public String getKeyword() {
		return keyword;
	}
	
	

}
