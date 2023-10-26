package com.zerobase.weather.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zerobase.weather.domain.Diary;
import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.repository.DiaryRepository;
import com.zerobase.weather.utils.OpenWeather;

@ExtendWith(MockitoExtension.class)
@DisplayName("날씨 일기 서비스 테스트")
class DiaryServiceTest {
	@Mock
	private DiaryRepository diaryRepository;
	
	@Mock
	private OpenWeather openWeather;
	
	@InjectMocks
	private DiaryService diaryService;
	
	@Test
	@DisplayName("날씨 일기 - 날씨 일기 생성")
	void weatherDiary_success() {
		// given
		Diary diary = Diary.builder()
				.weather("Clouds")
				.icon("04n")
				.temperature(289.16)
				.text("first Diary")
				.date(LocalDate.now())
				.build();
		

		// when
		Diary result = diaryRepository.save(diary);

		// then
		assertNull(result);
	}
	
	@Test
	@DisplayName("날씨 일기 - 해당 날짜 날씨 일기 목록 조회")
	void weatherDiary_listByDate() {
		// given
		given(diaryRepository.findAllByDate(any()))
			.willReturn(Arrays.asList(
				Diary.builder()
					.weather("Clear")
					.icon("01d")
					.temperature(293.52)
					.text("I want to learn spring boot")
					.build(),
				Diary.builder()
					.weather("Clouds")
					.icon("04d")
					.temperature(291.61)
					.text("I want to learn spring boot12313123123123")
					.build()
			))
		;

		// when
		List<DiaryDto> diaryDto = diaryService.findDiary(LocalDate.now());

		// then
		assertEquals(2, diaryDto.size());
		assertEquals("Clear", diaryDto.get(0).getWeather());
		assertEquals("01d", diaryDto.get(0).getIcon());
		assertEquals(293.52, diaryDto.get(0).getTemperature());
		assertEquals("Clouds", diaryDto.get(1).getWeather());
		assertEquals("04d", diaryDto.get(1).getIcon());
		assertEquals(291.61, diaryDto.get(1).getTemperature());
	}
}