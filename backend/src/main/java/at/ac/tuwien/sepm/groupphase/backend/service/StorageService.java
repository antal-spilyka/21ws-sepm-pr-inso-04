package at.ac.tuwien.sepm.groupphase.backend.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    /**
     * Stores a file.
     *
     * @param file to be stored.
     * @return file name of the stored file.
     */
    String store(MultipartFile file);

    /**
     * Loads a file.
     *
     * @param filename of the file to load.
     * @return the resource found under the given name.
     */
    Resource loadAsResource(String filename);
}
