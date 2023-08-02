package src.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Settings {
    @JsonProperty
    private String hostName;

    @JsonProperty
    private Map<Integer, Integer> buttonMapping;

    public String getHostname() {
        return hostName;
    }

    public Map<Integer, Integer> getButtonMapping() {
        return buttonMapping;
    }
}
