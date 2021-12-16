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
     * Finds room with matching properties.
     *
     * @param name containing properties to be searched for
     * @return List of rooms matching properties
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     */
    List<Hall> findHall(String name);

    /**
     * Gets Room by primary key (id).
     *
     * @param id of the room
     * @return corresponding room
     * @throws org.hibernate.service.spi.ServiceException                       when unknown error occurs
     * @throws at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException when room not found
     */
    Hall getById(Long id);

    /**
     * Method for returning all rooms from database.
     *
     * @return all the rooms stored in database
     */
    List<Hall> getAll();

    /**
     * Persists room.
     *
     * @param hallDto to be persisted
     * @return persisted room
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     */
    Hall save(HallDto hallDto);

    HallDetailDto getHall(String hallId);
}
