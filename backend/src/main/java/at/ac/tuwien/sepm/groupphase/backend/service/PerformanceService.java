package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PerformanceSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Performance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface PerformanceService {

    Performance save(PerformanceDto performanceDto);
    Stream<PerformanceDto> findPerformanceByDateTime(PerformanceSearchDto performanceSearchDto);
}
