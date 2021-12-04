package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDateTimeSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Room;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            Event persistedEvent = eventRepository.save(event);
            return eventMapper.entityToDto(persistedEvent);
        } catch (EntityExistsException e) {
            throw new ContextException(e);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<EventDto> findEvents(EventSearchDto eventSearchDto) {
        LOGGER.debug("Handeling in Service {}", eventSearchDto);
        if (eventSearchDto.getCategoryName() == null && eventSearchDto.getContent() == null
            && eventSearchDto.getDescription() == null && eventSearchDto.getDuration() == null) {
            throw new NotFoundException("No address was found for this query");
        }
        try {
            List<Event> events = eventRepository.findEvents(eventSearchDto.getDuration(), eventSearchDto.getContent(),
                eventSearchDto.getCategoryName(), eventSearchDto.getDescription(), PageRequest.of(0, 10));
            if (events.isEmpty()) {
                throw new NotFoundException("No events were found for this search query");
            }
            return events.stream().map(event ->
                eventMapper.entityToDto(event)
            ).collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<EventDto> findEventsByDateTime(EventDateTimeSearchDto eventDateTimeSearchDto) {
        LOGGER.debug("Handeling in Service {}", eventDateTimeSearchDto);
        try {
            List<Room> rooms = roomRepository.findRoom(eventDateTimeSearchDto.getRoom());
            List<Event> events = new ArrayList<>();
            List<Long> roomIds = new ArrayList<>();
            if (eventDateTimeSearchDto.getDateTime() != null) {
                LocalDateTime dateTimeFrom = eventDateTimeSearchDto.getDateTime().minusMinutes(30);
                LocalDateTime dateTimeTill = eventDateTimeSearchDto.getDateTime().plusMinutes(30);
                for (Room room : rooms) {
                    List<Event> eventsForRoom = eventRepository.findEventsWithDateTime(dateTimeFrom, dateTimeTill,
                        eventDateTimeSearchDto.getEvent(), room.getId());
                    events.addAll(eventsForRoom);
                    //roomIds.add(room.getId());
                }
                //events = eventRepository.findEventsWithDateTime(dateTimeFrom, dateTimeTill,
                //eventDateTimeSearchDto.getEvent(), roomIds, PageRequest.of(0, 10));
            } else {
                for (Room room : rooms) {
                    List<Event> eventsForRoom = eventRepository.findEventsWithoutDateTime(eventDateTimeSearchDto.getEvent(), room.getId());
                    events.addAll(eventsForRoom);
                }
                /*events = eventRepository.findEventsWithoutDateTime(eventDateTimeSearchDto.getEvent(), roomIds,
                    PageRequest.of(0, 10));*/
            }
            return events.stream().map(event ->
                eventMapper.entityToDto(event)
            ).collect(Collectors.toList());
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Transactional
    @Override
    public List<Event> findEvent(String name) {
        LOGGER.debug("Find events with name {}.", name);
        return this.eventRepository.findByNameAllIgnoreCaseContaining(name);
    }
}
