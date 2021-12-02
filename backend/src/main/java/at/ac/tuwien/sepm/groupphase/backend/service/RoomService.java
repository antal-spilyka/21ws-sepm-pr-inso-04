package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomInquiryDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.RoomSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Room;

import java.util.List;

/**
 * Service for working with Room.
 */
public interface RoomService {

    /**
     * Finds room with matching properties.
     *
     * @param roomSearchDto containing properties to be searched for
     * @return List of rooms matching properties
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     */
    public List<RoomDto> findRoom(RoomSearchDto roomSearchDto);

    /**
     * Gets Room by primary key (id).
     *
     * @param id of the room
     * @return corresponding room
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     * @throws at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException when room not found
     */
    RoomDto getById(Long id);

    /**
     * Methode for returning all rooms from database.
     *
     * @return all the rooms stored in database
     */
    List<RoomDto> getAll();

    /**
     * Persists room.
     *
     * @param roomInquiryDto to be persisted
     * @return persisted room
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     */
    RoomDto save(RoomInquiryDto roomInquiryDto);
}
