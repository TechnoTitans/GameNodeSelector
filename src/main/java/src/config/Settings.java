package src.config;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class Settings {
    public static final String HOSTNAME = "localhost";
    public static final String CLIENT_NAME = "TitanDash";
    public static final String NODE_NETWORK_TABLE = "GameNodeSelector";
    public static final String NODE_PUBLISH_TOPIC = "GridNodeId";
    public static final String SELECTED_AUTO_SUBSCRIBER_TOPIC = "GridNodeName";

    public static final String AUTO_NETWORK_TABLE = "AutoSelector";
    public static final String AUTO_PUBLISH_TOPIC = "SelectedAuto";
    public static final String AUTO_SUBSCRIBER_TOPIC = "AutoOptions";

    public static final String PROFILE_NETWORK_TABLE = "ProfileSelector";
    public static final String PROFILE_PUBLISH_TOPIC = "SelectedProfile";
    public static final String PROFILE_SUBSCRIBER_TOPIC = "ProfileOptions";

    public static final String ICON_PATH = "/titandashicon.png";

    public static final HashMap<Integer, Integer> BUTTON_MAPPING = new HashMap<>();
    static {
        BUTTON_MAPPING.put(30, 20);
        BUTTON_MAPPING.put(16, 21);
        BUTTON_MAPPING.put(2, 22);

        BUTTON_MAPPING.put(31, 23);
        BUTTON_MAPPING.put(17, 24);
        BUTTON_MAPPING.put(3, 25);

        BUTTON_MAPPING.put(32, 26);
        BUTTON_MAPPING.put(18, 27);
        BUTTON_MAPPING.put(4, 28);


        BUTTON_MAPPING.put(34, 10);
        BUTTON_MAPPING.put(20, 11);
        BUTTON_MAPPING.put(6, 12);

        BUTTON_MAPPING.put(35, 13);
        BUTTON_MAPPING.put(21, 14);
        BUTTON_MAPPING.put(7, 15);

        BUTTON_MAPPING.put(36, 16);
        BUTTON_MAPPING.put(22, 17);
        BUTTON_MAPPING.put(8, 18);


        BUTTON_MAPPING.put(38, 0);
        BUTTON_MAPPING.put(24, 1);
        BUTTON_MAPPING.put(10, 2);

        BUTTON_MAPPING.put(39, 3);
        BUTTON_MAPPING.put(25, 4);
        BUTTON_MAPPING.put(11, 5);

        BUTTON_MAPPING.put(40, 6);
        BUTTON_MAPPING.put(26, 7);
        BUTTON_MAPPING.put(12, 8);
    }

    public static URL getResource(final String path) {
        return Objects.requireNonNull(Settings.class.getResource(path));
    }

    public static ImageIcon getGridLayoutImage(final String nodeName) {
        return new ImageIcon(Settings.getResource("/grids/gridlayout-" + nodeName + ".png"));
    }
}
