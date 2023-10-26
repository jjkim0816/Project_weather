package com.zerobase.weather.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.weather.dto.GetDiary;
import com.zerobase.weather.service.DiaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DiaryController {
	private final DiaryService diaryService;
	
	@PostMapping("/create/diary")
	public void createDiary(
		@RequestParam @DateTimeFormat(iso = ISO.DATE)
			LocalDate date,
		@RequestBody String text
	) {
		diaryService.createDiary(date, text);
	}
	
	@GetMapping("/read/diary")
	public List<GetDiary> readDiary(
		@RequestParam @DateTimeFormat(iso = ISO.DATE)
			LocalDate date
	) {
		return diaryService.findDiary(date)
				.stream()
				.map(diaryDto -> GetDiary.builder()
						.id(diaryDto.getId())
						.weather(diaryDto.getWeather())
						.icon(diaryDto.getIcon())
						.temperature(diaryDto.getTemperature())
						.text(diaryDto.getText())
						.date(diaryDto.getDate())
						.build())
				.collect(Collectors.toList());
	}
	
	@GetMapping("/read/diaries")
	public List<GetDiary> readDiaries() {
		return null;
	}
	
	@PutMapping("/update/diary")
	public void updateDiary() {}
	
	@DeleteMapping("/delete/diary")
	public void deleteDiary() {}
}