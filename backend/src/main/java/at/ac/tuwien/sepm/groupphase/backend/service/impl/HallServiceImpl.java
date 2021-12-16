package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.HallMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventPlaceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.HallRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.HallService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {
    HallRepository hallRepository;
    HallMapper hallMapper;
    EventPlaceRepository eventPlaceRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public HallServiceImpl(HallRepository hallRepository, HallMapper hallMapper, EventPlaceRepository eventPlaceRepository) {
        this.hallRepository = hallRepository;
        this.hallMapper = hallMapper;
        this.eventPlaceRepository = eventPlaceRepository;
    }

    @Transactional
    @Override
    public List<Hall> findHall(String name) {
        LOGGER.debug("Handeling in Service {}", name);
        try {
            return hallRepository.findHall(name, PageRequest.of(0, 2));
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Hall getById(Long id) {
        LOGGER.debug("Handling in Service {}", id);
        try {
            return hallRepository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Hall> getAll() {
        LOGGER.debug("Handling in Service list of rooms");
        try {
            return hallRepository.getAllBy(PageRequest.of(0, 10));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public Hall save(HallDto hallDto) {
        LOGGER.debug("Handling in Service {}", hallDto);
        try {
            EventPlace eventPlace = eventPlaceRepository.findByIdEquals(hallMapper.dtoToEntity(hallDto).getEventPlace().getId());
            if (eventPlace == null) {
                throw new ContextException("EventPlace does not exist");
            }
            return hallRepository.save(hallMapper.dtoToEntity(hallDto));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public HallDetailDto getHall(String hallId) {
        LOGGER.debug("Handling in Service {}", hallId);
        try {
            Hall hall = hallRepository.getById(Long.parseLong(hallId));
            return hallMapper.entityToDetailDto(hall);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
