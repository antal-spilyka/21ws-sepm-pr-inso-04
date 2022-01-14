package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.HallSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Hall;

import java.util.List;

/**
 * Service for working with Room.
 */
public interface HallService {

    /**
     * Finds hall with matching properties.
     *
     * @param name containing properties to be searched for
     * @return List of halls matching properties
     */
    List<Hall> findHall(String name);

    /**
     * Returns every hall from the database.
     *
     * @return all the halls stored from database
     */
    List<Hall> getAll();

    /**
     * Persists hall.
     *
     * @param hallDto to be persisted
     * @return persisted hall
     */
    Hall save(HallDto hallDto);

    /**
     * Finds hall by primary key (id).
     *
     * @param hallId of the hall
     * @return corresponding hall
     */
    HallDetailDto getHall(String hallId);

    /**
     * Finds hall with matching properties.
     *
     * @param name containing properties to be searched for
     * @param eventPlaceId of the eventPlace
     * @return List of halls matching properties
     */
    List<Hall> findHallWithLocation(String name, Long eventPlaceId);
}
