package com.eventmate.service;

import com.eventmate.dto.StatisticsDto;

public interface StatisticsService {
    StatisticsDto getEventsNumberPerMonth();
    StatisticsDto getCategories();
    StatisticsDto getEventOffersAge();
    StatisticsDto getEventOffersGender();

}
