package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.EventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {

    private final PerformanceMapper performanceMapper;
    private final EventPlaceMapper eventPlaceMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public EventMapper(PerformanceMapper performanceMapper, EventPlaceMapper eventPlaceMapper) {
        this.performanceMapper = performanceMapper;
        this.eventPlaceMapper = eventPlaceMapper;
    }

    public EventDto entityToDto(Event event) {
        LOGGER.trace("Mapping {}", event);
        LOGGER.info("mapping " + event);
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setStartTime(event.getStartTime());
        eventDto.setDuration(event.getDuration());
        eventDto.setEventPlace(eventPlaceMapper.entityToDto(event.getEventPlace()));
        eventDto.setDescription(eventDto.getDescription());
        if (event.getPerformances() != null && 0 < event.getPerformances().size()) {
            // avoid cyclical calls
            List<PerformanceDto> performanceDtos = new ArrayList<>();
            for (Performance performance : event.getPerformances()) {
                performanceDtos.add(performanceMapper.entityToDto(performance, eventDto));
            }
            eventDto.setPerformances(performanceDtos);
        } else {
            eventDto.setPerformances(null);
        }
        LOGGER.info("mapped" + eventDto);
        return eventDto;
    }

    public Event dtoToEntity(EventDto eventDto) {
        LOGGER.trace("Mapping {}", eventDto);
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setStartTime(eventDto.getStartTime());
        event.setDuration(eventDto.getDuration());
        event.setEventPlace(eventPlaceMapper.dtoToEntity(eventDto.getEventPlace()));
        event.setDescription(eventDto.getDescription());
        if (eventDto.getPerformances() != null && 0 < eventDto.getPerformances().size()) {
            // avoid cyclical calls
            List<Performance> performances = new ArrayList<>();
            for (PerformanceDto performanceDto : eventDto.getPerformances()) {
                performances.add(performanceMapper.dtoToEntity(performanceDto, event));
            }
            event.setPerformances(performances);
        } else {
            event.setPerformances(null);
        }
        return event;
    }
}
