package src.config;

import java.util.HashMap;

public class Settings {
    public static final String hostName = "localhost";
    public static final String clientName = "TitanDash";
    public static final String nodeNetworkTable = "GameNodeSelector";
    public static final String nodePublishTopic = "Node";
    public static final String selectedAutoSubscriberTopic = "SelectedNode";

    public static final String autoNetworkTable = "AutoSelector";
    public static final String autoPublishTopic = "SelectedAuto";
    public static final String autoSubscriberTopic = "AutoOptions";

    public static final String profileNetworkTable = "ProfileSelector";
    public static final String profilePublishTopic = "SelectedProfile";
    public static final String profileSubscriberTopic = "ProfileOptions";

    public static final HashMap<Integer, Integer> buttonMapping = new HashMap<>();
    static {
        buttonMapping.put(30, 20);
        buttonMapping.put(16, 21);
        buttonMapping.put(2, 22);

        buttonMapping.put(31, 23);
        buttonMapping.put(17, 24);
        buttonMapping.put(3, 25);

        buttonMapping.put(32, 26);
        buttonMapping.put(18, 27);
        buttonMapping.put(4, 28);


        buttonMapping.put(34, 10);
        buttonMapping.put(20, 11);
        buttonMapping.put(6, 12);

        buttonMapping.put(35, 13);
        buttonMapping.put(21, 14);
        buttonMapping.put(7, 15);

        buttonMapping.put(36, 16);
        buttonMapping.put(22, 17);
        buttonMapping.put(8, 18);


        buttonMapping.put(38, 0);
        buttonMapping.put(24, 1);
        buttonMapping.put(10, 2);

        buttonMapping.put(39, 3);
        buttonMapping.put(25, 4);
        buttonMapping.put(11, 5);

        buttonMapping.put(40, 6);
        buttonMapping.put(26, 7);
        buttonMapping.put(12, 8);
    }
}
