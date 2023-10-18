package se.giron.moviecenter.core.service;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class CoverImageService {

    @Autowired
    private Environment env;

    public byte[] getImage(String fileName) throws IOException, FileNotFoundException {
        String assetsDirectory = this.env.getProperty("assets.directory");
        String coversDirName = this.env.getProperty("covers.dirname");
        String coversDirectory = assetsDirectory + File.separator + coversDirName + File.separator;

        String filename = coversDirectory + fileName;
        File file = new File(filename);

        if (!file.exists()) {
            throw new FileNotFoundException(fileName + " can not be found");
        }

        return IOUtils.toByteArray(Files.newInputStream(file.toPath()));
    }
}
