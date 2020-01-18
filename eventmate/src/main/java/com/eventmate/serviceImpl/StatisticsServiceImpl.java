package com.eventmate.serviceImpl;

import com.eventmate.dao.CategoryDao;
import com.eventmate.dao.EventDao;
import com.eventmate.dao.EventOfferDao;
import com.eventmate.dao.UserDao;
import com.eventmate.dto.StatisticsDto;
import com.eventmate.entity.ValueCount;
import com.eventmate.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final EventDao eventDao;
    private final UserDao userDao;
    private final EventOfferDao eventOfferDao;
    private final CategoryDao categoryDao;

    public StatisticsServiceImpl(EventDao eventDao, UserDao userDao, EventOfferDao eventOfferDao, CategoryDao categoryDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.eventOfferDao = eventOfferDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public StatisticsDto getEventsNumberPerMonth() {
        StatisticsDto statistic = new StatisticsDto();
        List<ValueCount> valueCounts = eventDao.countEventsByMonth();
        statistic.setLabels(valueCounts.stream().map( e -> e.getLabel().substring(0, 7)).collect(Collectors.toList()));
        statistic.setValues(valueCounts.stream().map( e -> e.getCount()).collect(Collectors.toList()));
        return statistic;
    }

    @Override
    public StatisticsDto getCategories() {
        StatisticsDto statistic = new StatisticsDto();
        List<ValueCount> valueCounts = categoryDao.countCategories();
        statistic.setLabels(valueCounts.stream().map( e -> e.getLabel()).collect(Collectors.toList()));
        statistic.setValues(valueCounts.stream().map( e -> e.getCount()).collect(Collectors.toList()));
        return statistic;
    }
}
