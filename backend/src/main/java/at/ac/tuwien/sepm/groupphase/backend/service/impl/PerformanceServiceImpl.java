package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
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
import java.util.stream.Stream;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    PerformanceRepository performanceRepository;
    ArtistRepository artistRepository;
    HallRepository hallRepository;
    ArtistMapper artistMapper;
    HallMapper hallMapper;
    PerformanceMapper performanceMapper;
    EventMapper eventMapper;
    EventRepository eventRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public PerformanceServiceImpl(PerformanceRepository performanceRepository, ArtistRepository artistRepository,
                                  HallRepository hallRepository, ArtistMapper artistMapper, HallMapper hallMapper,
                                  PerformanceMapper performanceMapper, EventMapper eventMapper, EventRepository eventRepository) {
        this.performanceRepository = performanceRepository;
        this.artistRepository = artistRepository;
        this.hallRepository = hallRepository;
        this.artistMapper = artistMapper;
        this.hallMapper = hallMapper;
        this.performanceMapper = performanceMapper;
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
    }

    @Transactional
    @Override
    public Performance save(PerformanceDto performanceDto) {
        LOGGER.debug("Handling in Service {}", performanceDto);
        if (
            performanceRepository.existsByNameAndEvent(performanceDto.getName(),
                eventMapper.dtoToEntity(performanceDto.getEventDto()))
        ) {
            throw new ContextException();
        }
        try {
            ArtistDto artistDto = performanceDto.getArtist();
            if (artistDto.getId() == null || artistRepository.getById(artistDto.getId()) == null) {
                Artist artist = artistMapper.dtoToEntity(artistDto);
                performanceDto.setArtist(artistMapper.entityToDto(artistRepository.save(artist)));
            }
            HallDto hallDto = performanceDto.getHall();
            if (hallDto.getId() == null || hallRepository.getById(hallDto.getId()) == null) {
                Hall hall = hallMapper.dtoToEntity(hallDto);
                performanceDto.setHall(hallMapper.entityToDto(hallRepository.save(hall)));
            }
            performanceDto.getEventDto().setPerformances(null);
            Performance performance = performanceMapper.dtoToEntity(performanceDto, eventMapper.dtoToEntity(performanceDto.getEventDto()));
            Performance performancePers = performanceRepository.save(performance);
            Event event = performancePers.getEvent();
            event.setDuration(event.getDuration() + performancePers.getDuration());
            List<Performance> eventPerformances = event.getPerformances() == null ? new ArrayList<Performance>() : event.getPerformances();
            eventPerformances.add(performancePers);
            event.setPerformances(eventPerformances);
            eventRepository.save(event);
            return performancePers;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Performance save(PerformanceDto performanceDto, Event event) {
        LOGGER.debug("Handeling in Service {}", performanceDto);
        try {
            ArtistDto artistDto = performanceDto.getArtist();
            if (artistDto.getId() == null || artistRepository.getById(artistDto.getId()) == null) {
                Artist artist = artistMapper.dtoToEntity(artistDto);
                performanceDto.setArtist(artistMapper.entityToDto(artistRepository.save(artist)));
            }
            HallDto hallDto = performanceDto.getHall();
            if (hallDto.getId() == null || hallRepository.getById(hallDto.getId()) == null) {
                Hall hall = hallMapper.dtoToEntity(hallDto);
                performanceDto.setHall(hallMapper.entityToDto(hallRepository.save(hall)));
            }
            //performanceDto.getEvent().setPerformances(null);
            Performance performance = performanceMapper.dtoToEntity(performanceDto, event);
            return performanceRepository.save(performance);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Stream<PerformanceDto> findPerformanceByDateTime(PerformanceSearchDto performanceSearchDto) {
        LOGGER.debug("Handling in Service {}", performanceSearchDto);
        try {
            List<Performance> performances;
            if (performanceSearchDto.getStartTime() != null) {
                LocalDateTime dateTimeFrom = performanceSearchDto.getStartTime().minusMinutes(30);
                LocalDateTime dateTimeTill = performanceSearchDto.getStartTime().plusMinutes(30);
                performances = performanceRepository.findPerformanceByDateTime(dateTimeFrom,
                    dateTimeTill, performanceSearchDto.getEventName(), performanceSearchDto.getHallName(), PageRequest.of(0, 10));
            } else {
                performances = performanceRepository.findPerformanceByEventAndHall(performanceSearchDto.getEventName(),
                    performanceSearchDto.getHallName(), PageRequest.of(0, 10));
            }
            LOGGER.info(performances.toString());
            return performances.stream().map(performance -> performanceMapper.entityToDto(performance, null));
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Stream<PerformanceDto> findPerformanceForArtist(Long id) {
        LOGGER.debug("Handling in service {}", id);
        try {
            List<Performance> performances = performanceRepository.findPerformanceForArtist(id, PageRequest.of(0, 15));
            return performances.stream().map(performance -> performanceMapper.entityToDto(performance, null));
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
