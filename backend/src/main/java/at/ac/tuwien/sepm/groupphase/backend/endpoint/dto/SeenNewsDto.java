package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import at.ac.tuwien.sepm.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepm.groupphase.backend.entity.News;

public class SeenNewsDto {
    private Long id;
    private ApplicationUser user;
    private News news;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser userId) {
        this.user = userId;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News newsId) {
        this.news = newsId;
    }
}
