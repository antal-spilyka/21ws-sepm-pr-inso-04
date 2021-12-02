package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.CategoryDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        ResponseEntity response = new ResponseEntity(categoryService.findCategory(categoryDto).stream(), HttpStatus.OK);
        return response;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new category.")
    public ResponseEntity saveCategory(@RequestBody @Validated CategoryDto categoryDto) {
        try {
            ResponseEntity response = new ResponseEntity(categoryService.save(categoryDto), HttpStatus.CREATED);
            return response;
        } catch (ContextException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category already exists:  " + e.getLocalizedMessage(), e);
        }
    }
}
