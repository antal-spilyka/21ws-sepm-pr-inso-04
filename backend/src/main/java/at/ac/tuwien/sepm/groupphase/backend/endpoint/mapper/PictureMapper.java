package at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PictureDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PictureMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public PictureDto entityToDto(Picture picture) {
        LOGGER.trace("Mapping: entityToDto(Picture picture)");
        PictureDto pictureDto = new PictureDto();
        pictureDto.setUrl(picture.getUrl());
        pictureDto.setId(picture.getId());
        return pictureDto;
    }

    public List<PictureDto> entityToDto(List<Picture> pictures) {
        LOGGER.trace("Mapping: entityToDto(List<Picture> pictures)");
        return pictures.stream().map(picture -> entityToDto(picture)).collect(Collectors.toList());
    }

    public Picture dtoToEntity(PictureDto pictureDto) {
        LOGGER.trace("Mapping: dtoToEntity(PictureDto pictureDto)");
        Picture picture = new Picture();
        picture.setUrl(pictureDto.getUrl());
        picture.setId(pictureDto.getId());
        return picture;
    }
}
