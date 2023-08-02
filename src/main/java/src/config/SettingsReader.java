package src.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SettingsReader {
    private final Settings settings;

    public SettingsReader() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final ClassLoader classLoader = getClass().getClassLoader();
        final File settingsFile = new File(
                Objects.requireNonNull(classLoader.getResource("settings.json")).getFile()
        );

        this.settings = objectMapper.readValue(
                settingsFile,
                Settings.class
        );
    }

    public Settings getSettings() {
        return settings;
    }
}
