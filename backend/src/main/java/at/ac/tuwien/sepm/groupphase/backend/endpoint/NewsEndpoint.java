package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.NewsDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final NewsMapper newsMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public NewsEndpoint(NewsService newsService, NewsMapper newsMapper) {
        this.newsService = newsService;
        this.newsMapper = newsMapper;
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Publish new news")
    public void save(@Validated @RequestBody NewsDto newsDto) {
        LOGGER.info("POST /api/v1/news body: {}", newsDto);
        newsService.save(newsMapper.dtoToEntity(newsDto));
    }

    /**
     * Get all news which were created in the last 7 days.
     *
     * @return observable list of found news.
     */
    @GetMapping
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDto> getNewNews() {
        LOGGER.info("GET /api/v1/news : newNews");
        try {
            return newsMapper.entityToDto(newsService.getNewNews());
        } catch (NotFoundException e) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading all news", e);
        }
    }
}
