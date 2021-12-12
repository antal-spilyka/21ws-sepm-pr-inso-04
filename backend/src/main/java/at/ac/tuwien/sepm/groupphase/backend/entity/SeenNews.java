package at.ac.tuwien.sepm.groupphase.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SeenNews {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ApplicationUser user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "news_id")
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

    @Override
    public String toString() {
        return "SeenNews{"
            + "id=" + id
            + ", user=" + user
            + ", news=" + news
            + '}';
    }
}
