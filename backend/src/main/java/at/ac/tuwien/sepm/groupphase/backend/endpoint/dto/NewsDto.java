package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class NewsDto {

    private Long id;

    private String headline;

    private EventDto event;

    private Long rating;

    private Long fsk;

    @NotNull(message = "createDate must not be null")
    private LocalDateTime createDate;

    private String shortDescription;
    private String longDescription;
    private List<PictureDto> pictures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventDto getEvent() {
        return event;
    }

    public List<PictureDto> getPictures() {
        return pictures;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getFsk() {
        return fsk;
    }

    public void setFsk(Long fsk) {
        this.fsk = fsk;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setPictures(List<PictureDto> pictures) {
        this.pictures = pictures;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public static final class NewsDtoBuilder {
        private EventDto event;
        private Long rating;
        private Long fsk;
        private LocalDateTime createDate;
        private String shortDescription;
        private String longDescription;
        private String headline;

        private NewsDtoBuilder() {
        }

        public static NewsDto.NewsDtoBuilder aNewsDto() {
            return new NewsDto.NewsDtoBuilder();
        }

        public NewsDto.NewsDtoBuilder withEvent(EventDto event) {
            this.event = event;
            return this;
        }

        public NewsDto.NewsDtoBuilder withRating(Long rating) {
            this.rating = rating;
            return this;
        }

        public NewsDto.NewsDtoBuilder withFsk(Long fsk) {
            this.fsk = fsk;
            return this;
        }

        public NewsDto.NewsDtoBuilder withCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public NewsDto.NewsDtoBuilder withShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public NewsDto.NewsDtoBuilder withLongDescription(String longDescription) {
            this.longDescription = longDescription;
            return this;
        }

        public NewsDto.NewsDtoBuilder withHeadline(String headline) {
            this.headline = headline;
            return this;
        }

        public NewsDto build() {
            NewsDto newsDto = new NewsDto();
            newsDto.setCreateDate(createDate);
            newsDto.setEvent(event);
            newsDto.setFsk(fsk);
            newsDto.setRating(rating);
            newsDto.setLongDescription(longDescription);
            newsDto.setShortDescription(shortDescription);
            newsDto.setHeadline(headline);
            return newsDto;
        }
    }
}
