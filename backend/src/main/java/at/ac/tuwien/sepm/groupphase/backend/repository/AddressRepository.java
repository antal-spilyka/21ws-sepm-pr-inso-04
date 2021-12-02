package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Address;
import at.ac.tuwien.sepm.groupphase.backend.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
