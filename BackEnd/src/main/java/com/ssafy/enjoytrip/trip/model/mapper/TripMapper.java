package com.ssafy.enjoytrip.trip.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.trip.model.GugunResponseDto;
import com.ssafy.enjoytrip.trip.model.PlanDto;
import com.ssafy.enjoytrip.trip.model.SearchRequestDto;
import com.ssafy.enjoytrip.trip.model.SearchResponseDto;

@Mapper
public interface TripMapper {

	public List<SearchResponseDto> search(SearchRequestDto searchRequestDto);
	public List<GugunResponseDto> getGugun(String areaCode);
	public List<SearchResponseDto> getAttractionInfoByLatLng(double startLat, double endLat, double startLng, double endLng);
	public void makeTripPersonal(PlanDto planDto);

}
