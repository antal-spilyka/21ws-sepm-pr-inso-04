package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.RoomMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Room;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventPlaceRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.RoomRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.RoomService;
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
public class RoomServiceImpl implements RoomService {
    RoomRepository roomRepository;
    RoomMapper roomMapper;
    EventPlaceRepository eventPlaceRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public RoomServiceImpl(RoomRepository roomRepository, RoomMapper roomMapper, EventPlaceRepository eventPlaceRepository) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.eventPlaceRepository = eventPlaceRepository;
    }

    @Transactional
    @Override
    public List<RoomDto> findRoom(RoomSearchDto roomSearchDto) {
        LOGGER.debug("Handeling in Service {}", roomSearchDto);
        try {
            EventPlace eventPlace = eventPlaceRepository.getById(roomSearchDto.getEventPlaceName());
            List<Room> rooms = roomRepository.findRoom(roomSearchDto.getName(), eventPlace, PageRequest.of(0, 2));
            return rooms.stream().map(room ->
                roomMapper.entityToDto(room)
            ).collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public RoomDto getById(Long id) {
        LOGGER.debug("Handeling in Service {}", id);
        try {
            return roomMapper.entityToDto(roomRepository.getById(id));
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public RoomDto save(RoomInquiryDto roomInquiryDto) {
        LOGGER.debug("Handeling in Service {}", roomInquiryDto);
        try {
            EventPlace eventPlace = eventPlaceRepository.getById(roomInquiryDto.getEventPlaceName());
            if (eventPlace == null) {
                throw new ContextException("EventPlace does not exist");
            }
            Room persistedRoom = roomRepository.save(roomMapper.dtoToEntity(roomInquiryDto, eventPlace));
            return roomMapper.entityToDto(persistedRoom);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
