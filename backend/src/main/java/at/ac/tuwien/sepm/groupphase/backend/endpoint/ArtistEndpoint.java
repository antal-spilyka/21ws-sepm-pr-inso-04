package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.ArtistSearchDto;
import at.ac.tuwien.sepm.groupphase.backend.exception.ContextException;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/v1/artists")
public class ArtistEndpoint {

    private ArtistService artistService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()); // todo logger verwenden

    public ArtistEndpoint(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find artist by search parameters.")
    public ResponseEntity findArtists(@Validated ArtistSearchDto artistSearchDto) {
        return new ResponseEntity(artistService.findArtist(artistSearchDto, 2).stream(), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/search")
    @Operation(summary = "Search artists by search parameters.")
    public ResponseEntity searchArtists(@Validated ArtistSearchDto artistSearchDto) {
        try {
            ResponseEntity response = new ResponseEntity(artistService.findArtist(artistSearchDto, 10).stream(), HttpStatus.OK);
            return response;
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
        }
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new artist.", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity saveArtist(@RequestBody @Validated ArtistDto artistDto) {
        try {
            ResponseEntity response = new ResponseEntity(artistService.save(artistDto), HttpStatus.CREATED);
            return response;
        } catch (ContextException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Artist already exists:  " + e.getLocalizedMessage(), e);
        }
    }
}
