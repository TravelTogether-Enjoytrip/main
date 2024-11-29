package com.ssafy.enjoytrip.trip.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrip.trip.model.GugunResponseDto;
import com.ssafy.enjoytrip.trip.model.PlanDto;
import com.ssafy.enjoytrip.trip.model.SearchRequestDto;
import com.ssafy.enjoytrip.trip.model.SearchResponseDto;
import com.ssafy.enjoytrip.trip.model.mapper.TripMapper;

@Service
public class TripServiceImpl implements TripService{
	
	@Autowired
	private TripMapper tripMapper;

	public TripServiceImpl(TripMapper tripMapper) {
		super();
		this.tripMapper = tripMapper;
	}

	@Override
	public List<SearchResponseDto> search(SearchRequestDto searchRequestDto) {
		return tripMapper.search(searchRequestDto);
	}

	@Override
	public List<GugunResponseDto> getGugun(String areaCode) {
		return tripMapper.getGugun(areaCode);
	}
	
	@Override
	public List<SearchResponseDto> getAttractionInfoByLatLng(double startLat, double endLat, double startLng, double endLng) {
		return tripMapper.getAttractionInfoByLatLng(startLat, endLat, startLng, endLng);
	}
	
	@Override
	public void makeTripPersonal(PlanDto planDto) {
		tripMapper.makeTripPersonal(planDto);
	}


}
