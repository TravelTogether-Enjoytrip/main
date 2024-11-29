package com.ssafy.enjoytrip.trip.model.service;

import java.util.List;

import com.ssafy.enjoytrip.trip.model.GugunResponseDto;
import com.ssafy.enjoytrip.trip.model.PlanDto;
import com.ssafy.enjoytrip.trip.model.SearchRequestDto;
import com.ssafy.enjoytrip.trip.model.SearchResponseDto;

public interface TripService {
	List<SearchResponseDto> search(SearchRequestDto searchRequestDto);
	List<GugunResponseDto> getGugun(String areaCode);
	List<SearchResponseDto> getAttractionInfoByLatLng(double startLat, double endLat, double startLng, double endLng);
	void makeTripPersonal(PlanDto planDto);
}

