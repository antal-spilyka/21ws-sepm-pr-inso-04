package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryEndpoint {

    private CategoryService categoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public CategoryEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find category by search parameters.")
    public ResponseEntity findCategory(CategoryDto categoryDto) {
        try {
            ResponseEntity response = new ResponseEntity(categoryService.findCategory(categoryDto).stream(), HttpStatus.OK);
            return response;
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occured.");
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new artist.")
    public ResponseEntity saveCategory(@RequestBody CategoryDto categoryDto) {
        try {
            ResponseEntity response = new ResponseEntity(categoryService.save(categoryDto), HttpStatus.CREATED);
            return response;
        } catch (ContextException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category with same name already exists.");
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occured.");
        }
    }
}
