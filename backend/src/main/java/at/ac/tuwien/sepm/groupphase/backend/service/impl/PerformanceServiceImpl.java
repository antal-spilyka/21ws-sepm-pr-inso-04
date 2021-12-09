package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.PerformanceMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.PerformanceRepository;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    PerformanceRepository performanceRepository;
    ArtistRepository artistRepository;
    HallRepository hallRepository;
    ArtistMapper artistMapper;
    HallMapper hallMapper;
    PerformanceMapper performanceMapper;
    EventMapper eventMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public PerformanceServiceImpl(PerformanceRepository performanceRepository, ArtistRepository artistRepository,
                                  HallRepository hallRepository, ArtistMapper artistMapper, HallMapper hallMapper,
                                  PerformanceMapper performanceMapper, EventMapper eventMapper) {
        this.performanceRepository = performanceRepository;
        this.artistRepository = artistRepository;
        this.hallRepository = hallRepository;
        this.artistMapper = artistMapper;
        this.hallMapper = hallMapper;
        this.performanceMapper = performanceMapper;
        this.eventMapper = eventMapper;
    }

    @Transactional
    @Override
    public Performance save(PerformanceDto performanceDto) {
        LOGGER.debug("Handling in Service {}", performanceDto);
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
        performanceDto.getEvent().setPerformances(null);
        Performance performance = performanceMapper.dtoToEntity(performanceDto, eventMapper.dtoToEntity(performanceDto.getEvent()));
        return performanceRepository.save(performance);
    }
}
