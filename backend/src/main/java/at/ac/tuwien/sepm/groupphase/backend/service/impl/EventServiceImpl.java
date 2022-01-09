package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.GeneralSearchEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    HallRepository hallRepository;
    EventMapper eventMapper;
    PerformanceMapper performanceMapper;
    ArtistRepository artistRepository;
    EventPlaceService eventPlaceService;
    PerformanceService performanceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventServiceImpl(EventRepository eventRepository, HallRepository hallRepository, EventMapper eventMapper,
                            PerformanceMapper performanceMapper, ArtistRepository artistRepository, EventPlaceService eventPlaceService,
                            PerformanceService performanceService) {
        this.eventRepository = eventRepository;
        this.hallRepository = hallRepository;
        this.eventMapper = eventMapper;
        this.performanceMapper = performanceMapper;
        this.artistRepository = artistRepository;
        this.eventPlaceService = eventPlaceService;
        this.performanceService = performanceService;
    }

    @Override
    public List<Event> findEvents(EventSearchDto eventSearchDto) {
        LOGGER.debug("Handling in Service {}", eventSearchDto);
        try {
            List<Event> events = eventRepository.findEvents(eventSearchDto.getDuration(), eventSearchDto.getDescription(), eventSearchDto.getCategory(),
                PageRequest.of(eventSearchDto.getPage(), 10));
            for (Event event : events) {
                event.setPerformances(null);
            }
            return events;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Event> findGeneralEvents(GeneralSearchEventDto generalSearchEventDto) {
        LOGGER.debug("Handling in Service {}", generalSearchEventDto);
        try {
            List<Event> events = eventRepository.findGeneralEvents(generalSearchEventDto.getSearchQuery(), PageRequest.of(generalSearchEventDto.getPage(), 10));
            for (Event event : events) {
                event.setPerformances(null);
            }
            return events;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public List<Event> findEvent(String name) {
        LOGGER.debug("Find events with name {}.", name);
        try {
            List<Event> filteredList = this.eventRepository.findByNameContainsIgnoreCase(name, PageRequest.of(0, 2));
            for (Event event : filteredList) {
                for (Performance performance : event.getPerformances()) {
                    //performance.setEvent(null);
                }
            }
            return filteredList;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Stream<PerformanceDto> getPerformances(Long id) {
        LOGGER.debug("Handling in service {}", id);
        try {
            Event event = eventRepository.getById(id);
            List<Performance> performances = event.getPerformances();
            List<Performance> performancesCopy = new ArrayList<>();
            for (Performance performance : performances) {
                Performance copyPerformance = new Performance(performance.getId(), performance.getName(), performance.getStartTime(),
                    performance.getDuration(), null, performance.getArtist(), performance.getHall());
                performancesCopy.add(copyPerformance);
            }
            Stream<PerformanceDto> performanceDtoStream = performancesCopy.stream().map(performance -> performanceMapper.entityToDto(performance, null));
            return performanceDtoStream;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Stream<PerformanceDto> getPerformancesByLocation(Long id, Integer page) {
        LOGGER.debug("Handling in service {}", id);
        try {
            List<Event> events = eventRepository.findEventsByLocation(id, PageRequest.of(page, 15));
            List<Performance> performancesCopy = new ArrayList<>();
            for (Event event : events) {
                List<Performance> performances = event.getPerformances();
                for (Performance performance : performances) {
                    Performance copyPerformance = new Performance(performance.getId(), performance.getName(), performance.getStartTime(),
                        performance.getDuration(), null, performance.getArtist(), performance.getHall());
                    performancesCopy.add(copyPerformance);
                }
            }
            Stream<PerformanceDto> performanceDtoStream = performancesCopy.stream().map(performance -> performanceMapper.entityToDto(performance, null));
            return performanceDtoStream;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Event saveEvent(EventDto eventDto) {
        LOGGER.debug("Update event by adding a performance {}", eventDto);
        if (eventDto == null) {
            throw new ServiceException("Event can't be null");
        }
        /*if (eventRepository.existsByName(eventDto.getName())) {
            throw new ContextException("Event name already exists");
        }*/
        try {
            long durationCounter = 0L;
            List<PerformanceDto> temp = new ArrayList<>();
            if (eventDto.getPerformances() != null && 0 < eventDto.getPerformances().size()) {
                for (PerformanceDto performanceDto : eventDto.getPerformances()) {
                    performanceDto.setStartTime(eventDto.getStartTime().plusMinutes(5 + durationCounter));
                    durationCounter += 5 + performanceDto.getDuration();
                    performanceDto.setEventDto(eventDto);
                    temp.add(performanceDto);

                }
                eventDto.setDuration(durationCounter);
            }
            Event event = eventRepository.save(eventMapper.dtoToEntity(eventDto));
            for (PerformanceDto performanceDto : temp) {
                performanceService.save(performanceDto, event);
            }
            return event;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
