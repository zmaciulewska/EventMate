package com.eventmate.serviceImpl;

import com.eventmate.dao.EventDao;
import com.eventmate.dao.EventOfferDao;
import com.eventmate.dao.UserDao;
import com.eventmate.dto.EventOfferDto;
import com.eventmate.dto.UserDto;
import com.eventmate.dto.form.EventOfferFormDto;
import com.eventmate.entity.Event;
import com.eventmate.entity.EventOffer;
import com.eventmate.entity.enumeration.Gender;
import com.eventmate.entity.enumeration.RoleName;
import com.eventmate.error.AppException;
import com.eventmate.error.Error;
import com.eventmate.mapper.EventOfferMapper;
import com.eventmate.service.EventOfferService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventOfferServiceImpl extends AbstractServiceImpl<EventOfferDto, EventOffer> implements EventOfferService {


    private final EventOfferMapper eventOfferMapper;
    private final EventOfferDao eventOfferDao;
    private final EventDao eventDao;
    private final UserDao userDao;

    public EventOfferServiceImpl(EventOfferMapper eventOfferMapper, EventOfferDao eventOfferDao, EventDao eventDao, UserDao userDao) {
        this.eventOfferMapper = eventOfferMapper;
        this.eventOfferDao = eventOfferDao;
        this.eventDao = eventDao;
        this.userDao = userDao;
    }

    @Override
    public JpaRepository<EventOffer, Long> getRepository() {
        return eventOfferDao;
    }

    @Override
    public EventOfferDto convert(EventOffer entity) {
        return eventOfferMapper.convert(entity);
    }

    @Override
    public EventOffer convert(EventOfferDto dto) {
        return eventOfferMapper.convert(dto);
    }

    @Override
    public List<EventOfferDto> entitiesToDtos(List<EventOffer> entities) {
        return entities.stream().map(eventOfferMapper::convert).collect(Collectors.toList());
    }

    @Override
    public List<EventOffer> dtosToEntities(List<EventOfferDto> dtos) {
        return dtos.stream().map(eventOfferMapper::convert).collect(Collectors.toList());
    }

    @Override
    public List<EventOfferDto> getAllEventOffers(Long eventId) {
        Event event = eventDao.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Object with given id: " + eventId + " not found."));
        if (event.getRemovalDate() != null) {
            throw new AppException(Error.EVENT_REMOVED);
        }

        List<EventOffer> eventOffers = eventOfferDao.findAllByEvent(event);
        return entitiesToDtos(eventOffers);
    }

    @Override
    public EventOfferDto create(EventOfferFormDto eventOfferForm, Long eventId) {
        Event event = eventDao.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Object with given id: " + eventId + " not found."));
        if (event.getRemovalDate() != null) {
            throw new AppException(Error.EVENT_REMOVED);
        }
        if (eventOfferForm.getPrefferedDate().isAfter(event.getEndDate())
                || eventOfferForm.getPrefferedDate().isBefore(event.getStartDate())) {
            throw new AppException(Error.PREFFERED_DATE_DOES_NOT_MATCH);
        }


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto principal = (UserDto) auth.getPrincipal();

        EventOffer eventOffer = new EventOffer();
        eventOffer.setOwner(userDao.findByEmail(principal.getEmail()));
        eventOffer.setEvent(event);
        eventOffer.setPrefferedGender(Gender.valueOf(eventOfferForm.getPrefferedGender()));
        eventOffer.setPrefferedMinAge(eventOfferForm.getPrefferedMinAge());
        eventOffer.setPrefferedMaxAge(eventOfferForm.getPrfferedMaxAge());
        eventOffer.setPrefferedLocalization(eventOfferForm.getPrefferedLocalization());
        eventOffer.setCreationDate(LocalDateTime.now());
        eventOffer.setPrefferedDate(eventOfferForm.getPrefferedDate());

        return convert(eventOfferDao.save(eventOffer));

    }

    @Override
    public EventOfferDto update(EventOfferFormDto eventOfferForm, Long id) {
        UserDto principal = getPrincipal();

        EventOffer eventOffer = findEventOfferById(id);
        if (!eventOffer.getOwner().getId().equals(principal.getId())) {
            throw new AppException(Error.USER_NOT_ALLOWED);
        }
        if (eventOfferForm.getPrefferedDate().isAfter(eventOffer.getEvent().getEndDate())
                || eventOfferForm.getPrefferedDate().isBefore(eventOffer.getEvent().getStartDate())) {
            throw new AppException(Error.PREFFERED_DATE_DOES_NOT_MATCH);
        }
        eventOffer.setPrefferedGender(Gender.valueOf(eventOfferForm.getPrefferedGender()));
        eventOffer.setPrefferedMinAge(eventOfferForm.getPrefferedMinAge());
        eventOffer.setPrefferedMaxAge(eventOfferForm.getPrfferedMaxAge());
        eventOffer.setPrefferedLocalization(eventOfferForm.getPrefferedLocalization());
        eventOffer.setPrefferedDate(eventOfferForm.getPrefferedDate());
        return convert(eventOfferDao.save(eventOffer));
    }

    private EventOffer findEventOfferById(Long id) {
        EventOffer eventOffer = eventOfferDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Object with given id: " + id + " not found."));
        return eventOffer;
    }

    @Override
    public void delete(Long id) {
        UserDto principal = getPrincipal();
        EventOffer eventOffer = findEventOfferById(id);
        if (!eventOffer.getOwner().getId().equals(principal.getId())
                && !principal.getAuthorities().contains(RoleName.ROLE_ADMIN)) {
            throw new AppException(Error.USER_NOT_ALLOWED);
        }
        eventOfferDao.delete(eventOffer);
    }
}
