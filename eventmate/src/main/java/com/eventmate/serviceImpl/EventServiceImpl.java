package com.eventmate.serviceImpl;

import com.eventmate.dao.CategoryDao;
import com.eventmate.dao.EventDao;
import com.eventmate.dao.UserDao;
import com.eventmate.dto.EventDto;
import com.eventmate.dto.UserDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.entity.Cost;
import com.eventmate.entity.Event;
import com.eventmate.entity.enumeration.RoleName;
import com.eventmate.error.AppException;
import com.eventmate.error.Error;
import com.eventmate.mapper.CostMapper;
import com.eventmate.mapper.EventMapper;
import com.eventmate.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl  extends AbstractServiceImpl<EventDto, Event> implements EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventMapper eventMapper;
    private final CostMapper costMapper;
    private final EventDao eventDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    public EventServiceImpl(EventMapper eventMapper, EventDao eventDao, UserDao userDao,
                            CategoryDao categoryDao,
                            CostMapper costMapper) {
        this.eventMapper = eventMapper;
        this.eventDao = eventDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.costMapper = costMapper;
    }

    @Override
    public EventDto create (EventFormDto eventForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();

        Event newEvent = new Event();
        newEvent.setTitle(eventForm.getTitle());
        newEvent.setDescription(eventForm.getDescription());
        newEvent.setLocalization(eventForm.getLocalization());
        newEvent.setStartDate(eventForm.getStartDate());
        newEvent.setEndDate(eventForm.getEndDate());
        newEvent.setCommon(eventForm.isCommon());
        newEvent.setAdministrator(userDao.findByEmail(principal.getEmail()));
        newEvent.setReporter(null);
        newEvent.setContinous(eventForm.isContinous());
        newEvent.setSiteUrl(eventForm.getSiteUrl());
        newEvent.setCreationDate(LocalDateTime.now());
        newEvent.setRemovalDate(null);
        newEvent.setCosts(eventForm.getCosts().stream()
                .map(costFormDto -> {
                    Cost entity = costMapper.formToEntity(costFormDto);
                    entity.setEvent(newEvent);
                    return entity;
                })
                .collect(Collectors.toSet()));
        newEvent.setCategories(eventForm.getCategoryIds().stream()
                .map(e -> categoryDao.findById(e).get())
                .collect(Collectors.toSet()));
        return eventMapper.convert(eventDao.save(newEvent));
    }

    @Override
    public EventDto createEventProposal(EventFormDto eventForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();

        Event newEvent = new Event();
        newEvent.setTitle(eventForm.getTitle());
        newEvent.setDescription(eventForm.getDescription());
        newEvent.setLocalization(eventForm.getLocalization());
        newEvent.setStartDate(eventForm.getStartDate());
        newEvent.setEndDate(eventForm.getEndDate());
        newEvent.setCommon(eventForm.isCommon());
        newEvent.setAdministrator(null);
        newEvent.setReporter(userDao.findByEmail(principal.getEmail()));
        newEvent.setContinous(eventForm.isContinous());
        newEvent.setSiteUrl(eventForm.getSiteUrl());
        newEvent.setCreationDate(LocalDateTime.now());
        newEvent.setRemovalDate(null);
        newEvent.setCosts(eventForm.getCosts().stream()
                .map(costFormDto -> {
                    Cost entity = costMapper.formToEntity(costFormDto);
                    entity.setEvent(newEvent);
                    return entity;
                })
                .collect(Collectors.toSet()));
        newEvent.setCategories(eventForm.getCategoryIds().stream()
                .map(e -> categoryDao.findById(e).get())
                .collect(Collectors.toSet()));
        return eventMapper.convert(eventDao.save(newEvent));
    }

    @Override
    public EventDto update(EventFormDto eventForm, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();

        Optional<Event> eventFindResult = getRepository().findById(id);
        if(eventFindResult.isPresent()) {
            Event existingEvent = eventFindResult.get();
            if(existingEvent.getReporter() != null) {
                if(!existingEvent.getReporter().getId().equals(principal.getId()))
                    throw new AppException(Error.USER_NOT_ALLOWED);
            } else {
                if(!principal.getAuthorities().contains(RoleName.ROLE_USER))
                    throw new AppException(Error.USER_NOT_ALLOWED);
            }

            existingEvent.setTitle(eventForm.getTitle());
            existingEvent.setDescription(eventForm.getDescription());
            existingEvent.setLocalization(eventForm.getLocalization());
            existingEvent.setStartDate(eventForm.getStartDate());
            existingEvent.setEndDate(eventForm.getEndDate());
            existingEvent.setCommon(eventForm.isCommon());
            //existingEvent.setAdministrator(userDao.findByEmail(principal.getEmail()));
            //existingEvent.setReporter(null);
            existingEvent.setContinous(eventForm.isContinous());
            existingEvent.setSiteUrl(eventForm.getSiteUrl());
            //existingEvent.setCreationDate(LocalDateTime.now());
            //newEvent.setRemovalDate(null);
            existingEvent.getCosts().clear();
            existingEvent.getCosts().addAll(eventForm.getCosts().stream()
                    .map(costFormDto -> {
                        Cost entity = costMapper.formToEntity(costFormDto);
                        entity.setEvent(existingEvent);
                        return entity;
                    })
                    .collect(Collectors.toSet()));
            existingEvent.setCategories(eventForm.getCategoryIds().stream()
                    .map(e -> categoryDao.findById(e).get())
                    .collect(Collectors.toSet()));
            return convert(getRepository().save(existingEvent));

        } else {
            return null;
        }
    }


    @Override
    public JpaRepository<Event, Long> getRepository() {
        return eventDao;
    }

    @Override
    public EventDto convert(Event entity) {
        return eventMapper.convert(entity);
    }

    @Override
    public Event convert(EventDto dto) {
        return eventMapper.convert(dto);
    }

    @Override
    public List<EventDto> entitiesToDtos(List<Event> entities) {
        return entities.stream().map(e -> eventMapper.convert(e)).collect(Collectors.toList());
    }

    @Override
    public List<Event> dtosToEntities(List<EventDto> dtos) {
        return dtos.stream().map(e -> eventMapper.convert(e)).collect(Collectors.toList());
    }
}
