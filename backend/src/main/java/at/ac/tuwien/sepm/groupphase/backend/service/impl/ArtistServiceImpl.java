package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.ArtistMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {

    private ArtistRepository artistRepository;
    private ArtistMapper artistMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ArtistServiceImpl(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    @Transactional
    @Override
    public List<Artist> findArtist(ArtistSearchDto artistSearchDto, Integer number) {
        LOGGER.debug("Handling in Service {}", artistSearchDto);
        try {
            if (number == 2) {
                return artistRepository.findArtist(artistSearchDto.getMisc(), PageRequest.of(0, number));
            } else {
                return artistRepository.searchArtist(artistSearchDto.getMisc(), PageRequest.of(0, number));
            }
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Artist getById(Long id) {
        LOGGER.debug("Handling in Service {}", id);
        try {
            return artistRepository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Artist save(ArtistDto artistDto) {
        LOGGER.debug("Handling in Service {}", artistDto);
        try {
            return artistRepository.save(artistMapper.dtoToEntity(artistDto));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
