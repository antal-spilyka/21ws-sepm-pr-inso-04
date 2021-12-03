package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.News;
import at.ac.tuwien.sepm.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public void save(News news) {
        LOGGER.debug("Publish new news {}", news);
        newsRepository.save(news);
    }

    @Override
    public List<News> getNewNews() {
        LOGGER.debug("Get all News");

        LocalDateTime beforeSevenDays = LocalDateTime.now().minusDays(7);
        return newsRepository.findByCreateDateAfter(beforeSevenDays);
    }
}
