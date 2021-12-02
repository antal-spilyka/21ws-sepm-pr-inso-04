package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class CategoryMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CategoryDto entityToDto(Category category) {
        LOGGER.trace("Mapping {}", category);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category dtoToEntity(CategoryDto categoryDto) {
        LOGGER.trace("Mapping {}", categoryDto);
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }
}
