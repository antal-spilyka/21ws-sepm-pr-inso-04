package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import at.ac.tuwien.sepm.groupphase.backend.entity.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository regarding Room.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Finds Room that contains name (LIKE) and has eventPlace (equals).
     *
     * @param name of the room to search for
     * @param eventPlace the room is in (exact)
     * @param pageable regulates number of results
     * @return List of corresponding Rooms
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    @Query("SELECT r FROM Room r WHERE UPPER(r.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
            "AND r.eventPlace = :eventPlace")
    List<Room> findRoom(@Param("name") String name, @Param("eventPlace") EventPlace eventPlace, Pageable pageable);

    /**
     * Gets Room by primary key (id).
     *
     * @param id of the room
     * @return corresponding room
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    Room getById(Long id);

    /**
     * Persists room.
     *
     * @param room to be persisted
     * @return persisted room
     */
    Room save(Room room);
}
