package com.eventmate.controller.v1;

import com.eventmate.dto.StatisticsDto;
import com.eventmate.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
@CrossOrigin
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/events-number-per-month")
    public StatisticsDto getEventsNumberPerMonth() {
        return statisticsService.getEventsNumberPerMonth();
    }

    @GetMapping("/categories")
    public StatisticsDto getCategories() {
        return statisticsService.getCategories();
    }
}
