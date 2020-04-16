package se.giron.moviecenter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import se.giron.moviecenter.core.configuration.IStorageConfiguration;

@Configuration
@ConfigurationProperties(prefix = "storage")
public class StorageConfiguration implements IStorageConfiguration {

    private String directory;

    @Override
    public String getDirectory() {
        return directory;
    }

    public StorageConfiguration setDirectory(String directory) {
        this.directory = directory;
        return this;
    }
}
