package src.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Settings {
    @JsonProperty
    private String hostName;

    @JsonProperty
    private String networkTable;

    @JsonProperty
    private String publishTopic;

    @JsonProperty
    private Map<Integer, Integer> buttonMapping;

    public String getHostname() {
        return hostName;
    }

    public String getNetworkTable() {
        return networkTable;
    }

    public String getPublishTopic() {
        return publishTopic;
    }

    public Map<Integer, Integer> getButtonMapping() {
        return buttonMapping;
    }
}
