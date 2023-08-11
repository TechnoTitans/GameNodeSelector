package src.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Settings {
    @JsonProperty
    private String hostName;

    @JsonProperty
    private String clientName;


    @JsonProperty
    private String nodeNetworkTable;

    @JsonProperty
    private String nodePublishTopic;


    @JsonProperty
    private String autoNetworkTable;

    @JsonProperty
    private String autoPublishTopic;

    @JsonProperty
    private String autoSubscriberTopic;

    @JsonProperty String selectedAutoSubscriberTopic;


    @JsonProperty
    private String profileNetworkTable;

    @JsonProperty
    private String profilePublishTopic;

    @JsonProperty
    private String profileSubscriberTopic;


    @JsonProperty
    private Map<Integer, Integer> buttonMapping;

    public String getHostname() {
        return hostName;
    }

    public String getClientName() {
        return clientName;
    }

    public String getNodeNetworkTable() {
        return nodeNetworkTable;
    }

    public String getNodePublishTopic() {
        return nodePublishTopic;
    }

    public String getSelectedAutoSubscriberTopic() {
        return selectedAutoSubscriberTopic;
    }

    public String getAutoNetworkTable() {
        return autoNetworkTable;
    }

    public String getAutoPublishTopic() {
        return autoPublishTopic;
    }

    public String getAutoSubscriberTopic() {
        return autoSubscriberTopic;
    }

    public String getProfileNetworkTable() {
        return profileNetworkTable;
    }

    public String getProfilePublishTopic() {
        return profilePublishTopic;
    }

    public String getProfileSubscriberTopic() {
        return profileSubscriberTopic;
    }

    public Map<Integer, Integer> getButtonMapping() {
        return buttonMapping;
    }
}
