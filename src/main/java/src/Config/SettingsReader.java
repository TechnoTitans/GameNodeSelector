package src.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SettingsReader {
    final Settings settings;

    public SettingsReader() throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();

        this.settings = objectMapper.readValue(
//                new File("settings.json"),
                new File("C:\\Users\\Max\\IdeaProjects\\GameNodeSelector\\src\\main\\resources\\settings.json"),
                Settings.class);
    }

    public Settings getSettings() {
        return settings;
    }
}
