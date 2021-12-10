package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDateTimeSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventPlaceService;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    HallRepository hallRepository;
    EventMapper eventMapper;
    ArtistRepository artistRepository;
    EventPlaceService eventPlaceService;
    PerformanceService performanceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventServiceImpl(EventRepository eventRepository, HallRepository hallRepository, EventMapper eventMapper,
                            ArtistRepository artistRepository, EventPlaceService eventPlaceService, PerformanceService performanceService) {
        this.eventRepository = eventRepository;
        this.hallRepository = hallRepository;
        this.eventMapper = eventMapper;
        this.artistRepository = artistRepository;
        this.eventPlaceService = eventPlaceService;
        this.performanceService = performanceService;
    }

    @Override
    public List<Event> findEvents(EventSearchDto eventSearchDto) {
        LOGGER.debug("Handling in Service {}", eventSearchDto);
        if (eventSearchDto.getDescription() == null && eventSearchDto.getDuration() == null) {
            throw new NotFoundException("No address was found for this query");
        }
        try {
            return eventRepository.findEvents(eventSearchDto.getDuration(), eventSearchDto.getDescription(),
                PageRequest.of(0, 10));
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /*@Override
    public List<Event> findEventsByDateTime(EventDateTimeSearchDto eventDateTimeSearchDto) {
        LOGGER.debug("Handling in Service {}", eventDateTimeSearchDto);
        try {
            List<Hall> halls = new ArrayList<>();
            List<Long> hallIds = new ArrayList<>();
            eventDateTimeSearchDto.getPerformances().forEach(performance -> halls.add(performance.getHall()));
            List<Event> events = new ArrayList<>();
            if (eventDateTimeSearchDto.getStartTime() != null) {
                LocalDateTime dateTimeFrom = eventDateTimeSearchDto.getStartTime().minusMinutes(30);
                LocalDateTime dateTimeTill = eventDateTimeSearchDto.getStartTime().plusMinutes(30);
                for (Hall hall : halls) {
                    /*List<Event> eventsForRoom = eventRepository.findEventsWithDateTime(dateTimeFrom, dateTimeTill,
                        eventDateTimeSearchDto.getEventName(), hall.getId());
                    events.addAll(eventsForRoom);
                    hallIds.add(hall.getId());
                }
                events = eventRepository.findEventsWithDateTime(dateTimeFrom, dateTimeTill,
                eventDateTimeSearchDto.getEvent(), hallIds, PageRequest.of(0, 10));
            } else {
                for (Hall hall : halls) {
                    List<Event> eventsForRoom = eventRepository.findEventsWithoutDateTime(eventDateTimeSearchDto.getEventName(), hall.getId());
                    events.addAll(eventsForRoom);
                }
                /*events = eventRepository.findEventsWithoutDateTime(eventDateTimeSearchDto.getEvent(), roomIds,
                    PageRequest.of(0, 10));
            }
            return events;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }*/

    @Transactional
    @Override
    public List<Event> findEvent(String name) {
        LOGGER.debug("Find events with name {}.", name);
        List<Event> filteredList = this.eventRepository.findByNameContainsIgnoreCase(name);
        for (Event event : filteredList) {
            for (Performance performance : event.getPerformances()) {
                performance.setEvent(null);
            }
        }
        return filteredList;
    }

    @Transactional
    @Override
    public Event saveEvent(EventDto event) {
        LOGGER.debug("Update event by adding a performance {}", event);
        long durationCounter = 0L;
        if (event != null) {
            if (event.getPerformances() != null && 0 < event.getPerformances().size()) {
                for (PerformanceDto performance : event.getPerformances()) {
                    performance.setStartTime(event.getStartTime().plusMinutes(5 + durationCounter));
                    durationCounter += 5 + performance.getDuration();
                    performanceService.save(performance);
                }
            }
            event.setDuration(durationCounter);
        }
        return eventRepository.save(eventMapper.dtoToEntity(event));
    }
}
