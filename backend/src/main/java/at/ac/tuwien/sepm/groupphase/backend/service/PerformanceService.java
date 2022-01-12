package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.BasketDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.GeneralSearchEventDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Event;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;
import at.ac.tuwien.sepm.groupphase.backend.entity.Sector;

import java.security.Principal;
import java.util.List;
import java.util.stream.Stream;

public interface PerformanceService {

    Performance save(PerformanceDto performanceDto);

    Performance save(PerformanceDto performanceDto, Event event);

    Stream<PerformanceDto> findPerformanceByDateTime(PerformanceSearchDto performanceSearchDto);

    Stream<PerformanceDto> findGeneralPerformanceByDateTime(GeneralSearchEventDto generalSearchEventDto);

    Stream<PerformanceDto> findPerformanceForArtist(Long id, Integer page);

    PerformanceDetailDto findPerformanceById(Long id);

    void buySeats(BasketDto basket, Long performanceId, Principal principal);

    List<Sector> testPrice(Integer price);

    void reserveSeats(BasketDto basket, Long performanceId, Principal principal);
}
