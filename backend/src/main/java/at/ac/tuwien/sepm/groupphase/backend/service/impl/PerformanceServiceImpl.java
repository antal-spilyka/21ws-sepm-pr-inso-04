package at.ac.tuwien.sepm.groupphase.backend.service.impl;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public PerformanceServiceImpl(PerformanceRepository performanceRepository, ArtistRepository artistRepository, HallRepository hallRepository) {
        this.performanceRepository = performanceRepository;
        this.artistRepository = artistRepository;
        this.hallRepository = hallRepository;
    }

    @Transactional
    @Override
    public Performance save(Performance performance) {
        LOGGER.debug("Handeling in Service {}", performance);
        Artist artist = performance.getArtist();
        if (artist.getId() == null || artistRepository.getById(artist.getId()) == null) {
            artistRepository.save(artist);
        }
        Hall hall = performance.getHall();
        if (hall.getId() == null || hallRepository.getById(hall.getId()) == null) {
            hallRepository.save(hall);
        }
        return performanceRepository.save(performance);
    }
}
