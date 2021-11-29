package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/api/v1/artists")
public class ArtistEndpoint {

    private ArtistService artistService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ArtistEndpoint(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find artist by search parameters.")
    public ResponseEntity findArtists(ArtistSearchDto artistSearchDto) {
        try {
            ResponseEntity response = new ResponseEntity(artistService.findArtist(artistSearchDto).stream(), HttpStatus.OK);
            return response;
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occured.");
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new artist.", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity saveArtist(@RequestBody ArtistDto artistDto) {
        try {
            ResponseEntity response = new ResponseEntity(artistService.save(artistDto), HttpStatus.CREATED);
            return response;
        } catch (ContextException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Artist with same id already exists.");
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error occured.");
        }
    }
}
