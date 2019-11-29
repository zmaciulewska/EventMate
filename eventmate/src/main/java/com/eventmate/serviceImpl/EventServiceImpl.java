package com.eventmate.serviceImpl;

import com.eventmate.dao.CategoryDao;
import com.eventmate.dao.EventDao;
import com.eventmate.dao.UserDao;
import com.eventmate.dto.EventDto;
import com.eventmate.dto.UserDto;
import com.eventmate.dto.form.EventFormDto;
import com.eventmate.entity.Cost;
import com.eventmate.entity.Event;
import com.eventmate.entity.User;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl extends AbstractServiceImpl<EventDto, Event> implements EventService {

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
    public EventDto create(EventFormDto eventForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();

        Event newEvent = new Event();
        newEvent.setTitle(eventForm.getTitle());
        newEvent.setDescription(eventForm.getDescription());
        newEvent.setLocalization(eventForm.getLocalization());
        newEvent.setStartDate(eventForm.getStartDate());
        newEvent.setEndDate(eventForm.getEndDate());
        newEvent.setCommon(eventForm.isCommon());
        if (eventForm.isCommon() && principal.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name()))) {
            // admin can create public event without any confirmation
            newEvent.setAdministrator(userDao.findByEmail(principal.getEmail()));
            newEvent.setReporter(null);
        } else {
            // user can create public event proposal, it have to be confirmed by admin
            // private events don't require confirmation
            newEvent.setAdministrator(null);
            newEvent.setReporter(userDao.findByEmail(principal.getEmail()));
        }
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
    public void delete(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();


        Event event = findEventById(id);
        if (event.getRemovalDate() != null) {
            throw new AppException(Error.EVENT_REMOVED);
        }
        validateUserResourcesAccess(principal, event);
        event.setRemovalDate(LocalDateTime.now());
        eventDao.save(event);
    }

    private void validateUserResourcesAccess(UserDto principal, Event event) {
        if (event.getReporter() != null) {
            if (!event.getReporter().getId().equals(principal.getId()) &&
                    !principal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name())))
                throw new AppException(Error.USER_NOT_ALLOWED);
        } else {

            if (!principal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.name())))
                throw new AppException(Error.USER_NOT_ALLOWED);
        }
    }


    @Override
    public EventDto update(EventFormDto eventForm, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();

        Event existingEvent = findEventById(id);
        if (existingEvent.getRemovalDate() != null) {
            throw new AppException(Error.EVENT_REMOVED);
        }
        validateUserResourcesAccess(principal, existingEvent);
        existingEvent.setTitle(eventForm.getTitle());
        existingEvent.setDescription(eventForm.getDescription());
        existingEvent.setLocalization(eventForm.getLocalization());
        existingEvent.setStartDate(eventForm.getStartDate());
        existingEvent.setEndDate(eventForm.getEndDate());
        existingEvent.setCommon(eventForm.isCommon());
        existingEvent.setContinous(eventForm.isContinous());
        existingEvent.setSiteUrl(eventForm.getSiteUrl());
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
    }

    @Override
    public List<EventDto> getAll() {
        return entitiesToDtos(eventDao.findAllByRemovalDateNull());
    }

    @Override
    public EventDto confirmPublicEventProposal(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();

        Event event = findEventById(id);
        if (event.getAdministrator() != null) {
            throw new AppException(Error.EVENT_ALREADY_CONFIRMED);
        }
        if (!event.isCommon()) {
            throw new AppException(Error.CANNOT_CONFIRM_PIVATE_EVENT);
        }
        event.setAdministrator(userDao.findByEmail(principal.getEmail()));
        return convert(getRepository().save(event));
    }

    @Override
    public List<EventDto> getAllNotConfirmed() {
        return entitiesToDtos(eventDao.findAllByRemovalDateNullAndAdministratorNullAndCommon(new Boolean(true)));
    }

    @Override
    public List<EventDto> getConfirmedOrPrivate() {
        return entitiesToDtos(eventDao.findAllByRemovalDateNullAndAdministratorNotNullOrCommonAndRemovalDateNull(new Boolean(false)));
    }

    @Override
    public List<EventDto> getUserEvents(User user) {
        return entitiesToDtos(eventDao.findAllByRemovalDateNullAndReporter(user));
    }

    private Event findEventById(Long id) {
        Event event = eventDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Object with given id: " + id + " not found."));
        if (event.getRemovalDate() != null) {
            throw new AppException(Error.EVENT_REMOVED);
        }
        return event;
    }

    @Override
    public EventDto getOne(Long id) {
        return convert(findEventById(id));
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
