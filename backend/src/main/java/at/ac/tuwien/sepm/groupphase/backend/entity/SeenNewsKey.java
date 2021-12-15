package at.ac.tuwien.sepm.groupphase.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class SeenNewsKey implements Serializable {
    private Long user;
    private Long news;

    public SeenNewsKey(Long user, Long news) {
        this.user = user;
        this.news = news;
    }

    public SeenNewsKey() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeenNewsKey that = (SeenNewsKey) o;
        return Objects.equals(user, that.user) && Objects.equals(news, that.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, news);
    }
}


