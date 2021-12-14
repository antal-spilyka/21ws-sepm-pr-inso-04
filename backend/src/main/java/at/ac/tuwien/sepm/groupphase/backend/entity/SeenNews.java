package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@IdClass(SeenNewsKey.class)
public class SeenNews implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private ApplicationUser user;

    @Id
    @ManyToOne
    @JoinColumn(name = "newsId", nullable = false)
    private News news;

    public SeenNews(ApplicationUser user, News news) {
        this.user = user;
        this.news = news;
    }

    public SeenNews() {

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

    @Override
    public String toString() {
        return "SeenNews{"
            + ", user=" + user
            + ", news=" + news
            + '}';
    }
}
