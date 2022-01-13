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
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String BASE_URL = "/api/v1/artist";

    public ArtistEndpoint(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Secured("ROLE_USER")
    @GetMapping
    @Operation(summary = "Find artist by search parameters.")
    public ResponseEntity findArtists(@Validated ArtistSearchDto artistSearchDto) {
        LOGGER.info("GET " + BASE_URL + " " + artistSearchDto.toString());
        return new ResponseEntity(artistService.findArtist(artistSearchDto, 2).stream(), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/search")
    @Operation(summary = "Search artists by search parameters.")
    public ResponseEntity searchArtists(@Validated ArtistSearchDto artistSearchDto) {
        LOGGER.info("GET " + BASE_URL + "/search " + artistSearchDto.toString());
        ResponseEntity response = new ResponseEntity(artistService.findArtist(artistSearchDto, 10).stream(), HttpStatus.OK);
        return response;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @Operation(summary = "persist new artist.", security = @SecurityRequirement(name = "apiKey"))
    public ResponseEntity saveArtist(@RequestBody @Validated ArtistDto artistDto) {
        LOGGER.info("POST " + BASE_URL + " " + artistDto.toString());
        ResponseEntity response = new ResponseEntity(artistService.save(artistDto), HttpStatus.CREATED);
        return response;
    }
}
