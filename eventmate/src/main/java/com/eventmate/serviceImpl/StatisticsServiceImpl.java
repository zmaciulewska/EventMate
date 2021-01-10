package com.eventmate.serviceImpl;

import com.eventmate.dao.CategoryDao;
import com.eventmate.dao.EventDao;
import com.eventmate.dao.EventOfferDao;
import com.eventmate.dao.UserDao;
import com.eventmate.dto.StatisticsDto;
import com.eventmate.entity.MinMaxCount;
import com.eventmate.entity.ValueCount;
import com.eventmate.entity.enumeration.Gender;
import com.eventmate.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.fill;

@Service
public class
StatisticsServiceImpl implements StatisticsService {

    /*private static final String femalePl = "Kobiety";
    private static final String malePl = "Mężczyźni";*/


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
        statistic.setLabels(valueCounts.stream().map(e -> e.getLabel().substring(0, 7)).collect(Collectors.toList()));
        statistic.setValues(valueCounts.stream().map(e -> e.getCount()).collect(Collectors.toList()));
        return statistic;
    }

    @Override
    public StatisticsDto getCategories() {
        StatisticsDto statistic = new StatisticsDto();
        List<ValueCount> valueCounts = categoryDao.countCategories();
        statistic.setLabels(valueCounts.stream().map(e -> e.getLabel()).collect(Collectors.toList()));
        statistic.setValues(valueCounts.stream().map(e -> e.getCount()).collect(Collectors.toList()));
        return statistic;
    }

    @Override
    public StatisticsDto getEventOffersAge() {

        Integer minAge = eventOfferDao.getMinAge();
        Integer maxAge = eventOfferDao.getMaxAge();


        Integer[] ageArray = new Integer[maxAge + 1];
        fill(ageArray, 0);

        List<MinMaxCount> minMaxCountList = eventOfferDao.getMinMaxAgeCount();
        for (MinMaxCount e : minMaxCountList) {
            if (e.getMin().equals(e.getMax())) {
                ageArray[e.getMin()] += e.getCount();
            } else {
                for (int i = e.getMin(); i < e.getMax(); i++) {
                    ageArray[i] += e.getCount();
                }
            }
        }

        StatisticsDto statistic = new StatisticsDto();
        List<String> labels = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (int i = minAge; i <= maxAge; i++) {
            labels.add(String.valueOf(i));
            values.add(ageArray[i]);
        }

        statistic.setLabels(labels);
        statistic.setValues(values);
        return statistic;
    }

    @Override
    public StatisticsDto getEventOffersGender() {
        StatisticsDto statistic = new StatisticsDto();
        List<ValueCount> valueCounts = eventOfferDao.getGenderCount();
        statistic.setLabels(valueCounts.stream().map(e -> mapGender(e.getLabel())).collect(Collectors.toList()));
        statistic.setValues(valueCounts.stream().map(e -> e.getCount()).collect(Collectors.toList()));
        return  statistic;
    }

    private String mapGender(String genderEn) {
        switch (genderEn) {
            case "MALE":
                return Gender.Mężczyźni.name();
            case "FEMALE":
                return Gender.Kobiety.name();
            default:
                return Gender.Obydwie.name();
        }
    }
}
