package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepm.groupphase.backend.service.StorageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {
    private final Path rootLocation = Paths.get("./uploads/");

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    public StorageServiceImpl() {
        File directory = new File(rootLocation.toString());
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    @Override
    public String store(MultipartFile file) {
        LOGGER.debug("Storing file");
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFilename;
        try {
            if (file.isEmpty()) {
                throw new ServiceException("Failed to store empty file " + originalFilename);
            }
            if (originalFilename.contains("..")) {
                // This is a security check
                throw new IllegalArgumentException(
                    "Cannot store file with relative path outside current directory "
                        + originalFilename);
            }

            String[] splitted = originalFilename.split("\\.");
            String ending = splitted[splitted.length - 1];

            do {
                newFilename = (RandomStringUtils.randomAlphanumeric(64) + "." + ending);
            } while (new File(newFilename).exists());

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(newFilename),
                    StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new ServiceException("Failed to store file " + originalFilename, e);
        }

        return newFilename;
    }

    @Override
    public Resource loadAsResource(String filename) {
        LOGGER.debug("Loading File as Resource");
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new NotFoundException(
                    "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Could not read file: " + filename, e);
        }
    }
}
