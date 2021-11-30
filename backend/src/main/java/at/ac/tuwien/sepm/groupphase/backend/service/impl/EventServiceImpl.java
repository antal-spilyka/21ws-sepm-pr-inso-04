package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Room;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.repository.ArtistRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.CategoryRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepm.groupphase.backend.repository.RoomRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.EventService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;
    CategoryRepository categoryRepository;
    RoomRepository roomRepository;
    EventMapper eventMapper;
    ArtistRepository artistRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventServiceImpl(
        EventRepository eventRepository,
        CategoryRepository categoryRepository,
        RoomRepository roomRepository,
        EventMapper eventMapper,
        ArtistRepository artistRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.roomRepository = roomRepository;
        this.eventMapper = eventMapper;
        this.artistRepository = artistRepository;
    }

    @Transactional
    @Override
    public EventDto createEvent(EventInquiryDto eventInquiryDto) {
        LOGGER.debug("Handeling in Service {}", eventInquiryDto);
        try {
            Category category = categoryRepository.getByName(eventInquiryDto.getCategoryName());
            Room room = roomRepository.getById(eventInquiryDto.getRoomId());
            Artist artist = artistRepository.getById(eventInquiryDto.getArtistId());

            if (category == null) {
                throw new ContextException("Category doesn't exist.");
            }

            Event event = eventMapper.inquiryDtoToEntity(eventInquiryDto, room, category, artist);
            Event persistedEvent =  eventRepository.save(event);
            return eventMapper.entityToDto(persistedEvent);
        } catch (EntityExistsException e) {
            throw new ContextException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
