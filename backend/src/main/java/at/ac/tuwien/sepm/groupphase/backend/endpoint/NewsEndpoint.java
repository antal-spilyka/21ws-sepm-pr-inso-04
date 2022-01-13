package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SimpleSeenNewsDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.PermitAll;
import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsEndpoint {
    private final NewsService newsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_URL = "/api/v1/news";

    @Autowired
    public NewsEndpoint(NewsService newsService) {
        this.newsService = newsService;
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Publish new news")
    public void save(@Validated @RequestBody NewsDto newsDto) {
        LOGGER.info("POST " + BASE_URL + " " + newsDto.toString());
        newsService.save(newsDto);
    }

    /**
     * Get all news which were created in the last 7 days.
     *
     * @return observable list of found news.
     */
    @GetMapping("/{email}/new")
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    public List<SimpleNewsDto> getNewNews(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}/new", email);
        return newsService.getNewNews(email);
    }

    @GetMapping("/{email}/old")
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    public List<SimpleNewsDto> getOldNews(@PathVariable String email) {
        LOGGER.info("GET " + BASE_URL + "/{}/old", email);
        return newsService.getOldNews(email);
    }

    @PostMapping("/read")
    @PermitAll
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto readNews(@RequestBody SimpleSeenNewsDto simpleSeenNewsDto) {
        LOGGER.info("POST " + BASE_URL + "/read" + " " + simpleSeenNewsDto.toString());
        return newsService.readNews(simpleSeenNewsDto);
    }
}
