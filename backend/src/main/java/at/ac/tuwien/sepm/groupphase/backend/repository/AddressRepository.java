package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import at.ac.tuwien.sepm.groupphase.backend.entity.EventPlace;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository regarding Address.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    /**
     * Gets address by primary key (id).
     *
     * @param id of the address
     * @return fitting address
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    Address getById(Long id);

    /**
     * Persists address.
     *
     * @param address to be persisted
     * @return persisted address
     */
    Address save(Address address);

    @Query("SELECT a FROM Address a WHERE a.zip = :zip OR UPPER(a.city) " +
        "LIKE UPPER(CONCAT( '%', :city, '%')) OR UPPER(a.state) LIKE UPPER(CONCAT( '%', :state, '%'))" +
        "OR UPPER(a.description) LIKE UPPER(CONCAT( '%', :description, '%')) OR UPPER(a.country) LIKE " +
        "UPPER(CONCAT( '%', :country, '%')) OR UPPER(a.street) LIKE UPPER(CONCAT( '%', :street, '%'))")
    List<Address> findEventLocation(@Param("city") String city, @Param("state") String state, @Param("country") String country,
                                       @Param("description") String description, @Param("street") String street,
                                       @Param("zip") Integer zip, Pageable pageable);
}
