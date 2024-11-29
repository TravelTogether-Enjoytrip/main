package com.ssafy.enjoytrip.trip.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.group.model.GroupDto;
import com.ssafy.enjoytrip.trip.model.GugunResponseDto;
import com.ssafy.enjoytrip.trip.model.PlanDto;
import com.ssafy.enjoytrip.trip.model.SearchRequestDto;
import com.ssafy.enjoytrip.trip.model.SearchResponseDto;
import com.ssafy.enjoytrip.trip.model.service.TripService;

@RestController
@RequestMapping("/tripapi")
@CrossOrigin("*")
public class TripController {
	private final TripService tripService;
	
	public TripController(TripService tripService) {
		super();
		this.tripService = tripService;
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<SearchResponseDto>> getAttractionInfo(
	        @RequestParam int areaCode,
	        @RequestParam int gugun,
	        @RequestParam int contentTypeId,
	        @RequestParam String keyword) {
		SearchRequestDto searchRequestDto = new SearchRequestDto();
		searchRequestDto.setAreaCode(areaCode);
		searchRequestDto.setGugun(gugun);
		searchRequestDto.setContentTypeId(contentTypeId);
		searchRequestDto.setKeyword(keyword);
		
	    List<SearchResponseDto> searchResponseDto = tripService.search(searchRequestDto);
	    return ResponseEntity.ok(searchResponseDto);
	}
	
	@GetMapping("/gugun")
	public ResponseEntity<List<GugunResponseDto>> getGugun(@RequestParam String areaCode){
		List<GugunResponseDto> gugunInfo = tripService.getGugun(areaCode);
		return ResponseEntity.ok(gugunInfo);
	}
	
	@GetMapping("/searchbyLatLng")
	public ResponseEntity<?> getAttractionInfoByLatLng(
	        @RequestParam double startLat,
	        @RequestParam double endLat,
	        @RequestParam double startLng,
	        @RequestParam double endLng) {
		List<SearchResponseDto> list = new ArrayList<>();
		try {
			list = tripService.getAttractionInfoByLatLng(startLat, endLat, startLng, endLng);
			return new ResponseEntity<List>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/maketrippersonal")
	public ResponseEntity<?> makeTripPersonal(@RequestBody PlanDto planDto){
		System.out.println("------------------------------");
		for (int i = 0; i < planDto.getPlan().size(); i++) {
			for (int j = 0; j < planDto.getPlan().get(i).size(); j++) {
				System.out.println(planDto.getPlan().get(i).get(j));
			}
		}
		System.out.println("------------------------------");
		try {
			tripService.makeTripPersonal(planDto);
			return new ResponseEntity<PlanDto>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

