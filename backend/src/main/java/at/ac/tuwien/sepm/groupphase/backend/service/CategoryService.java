package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;

import java.util.List;

/**
 * Service for working with Category.
 */
public interface CategoryService {

    /**
     * Finds category with matching properties.
     *
     * @param categoryDto containing properties to be searched for
     * @return List of categories matching properties
     * @throws org.hibernate.service.spi.ServiceException when unknown error occurs
     */
    List<CategoryDto> findCategory(CategoryDto categoryDto);

    /**
     * Persists category.
     *
     * @param categoryDto to be persisted
     * @return persisted category
     * @throws org.hibernate.service.spi.ServiceException                      when unknown error occurs
     * @throws at.ac.tuwien.sepm.groupphase.backend.exception.ContextException when artist already exists
     */
    CategoryDto save(CategoryDto categoryDto);
}
