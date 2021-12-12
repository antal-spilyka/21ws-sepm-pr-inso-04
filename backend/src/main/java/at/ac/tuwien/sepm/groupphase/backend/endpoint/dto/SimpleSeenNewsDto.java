package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class SimpleSeenNewsDto {
    private String userEmail;
    private Long newsId;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
}
