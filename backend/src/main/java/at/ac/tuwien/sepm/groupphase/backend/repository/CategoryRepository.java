package at.ac.tuwien.sepm.groupphase.backend.repository;

import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository regarding Category.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    /**
     * Finds category that contains name.
     *
     * @param name of the category
     * @param pageable regulates the number of results
     * @return list of fitting categories
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    @Query("SELECT n FROM Category n WHERE UPPER(n.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<Category> findCategory(@Param("name") String name, Pageable pageable);

    /**
     * Gets Category by primary key (name).
     *
     * @param name of the category
     * @return corresponding Category
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    Category getByName(String name);

    /**
     * Persists artist.
     *
     * @param category to be persisted
     * @return persisted category
     * @throws javax.persistence.PersistenceException when unknown error occurs
     */
    Category save(Category category);
}
