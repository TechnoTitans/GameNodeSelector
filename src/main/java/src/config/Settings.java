package src.config;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Settings {
    public static final String HOSTNAME = "localhost";
    public static final String CLIENT_NAME = "TitanDash";
    public static final String NODE_NETWORK_TABLE = "GameNodeSelector";
    public static final String NODE_PUBLISH_TOPIC = "GridNodeId";
    public static final String SELECTED_NODE_SUBSCRIBER_TOPIC = "GridNodeName";

    public static final String AUTO_NETWORK_TABLE = "AutoSelector";
    public static final String AUTO_SUBSCRIBER_TOPIC = "AutoOptions";
    public static final String AUTO_PUBLISH_TOPIC = "SelectedAuto";

    public static final String PROFILE_NETWORK_TABLE = "ProfileSelector";
    public static final String PROFILE_PUBLISH_TOPIC = "SelectedProfile";
    public static final String PROFILE_SUBSCRIBER_TOPIC = "ProfileOptions";

    public static final String ICON_PATH = "/titandashicon.png";

    public static final Map<Integer, Integer> BUTTON_MAPPING = Map.ofEntries(
            Map.entry(30, 20),
            Map.entry(16, 21),
            Map.entry(2, 22),

            Map.entry(31, 23),
            Map.entry(17, 24),
            Map.entry(3, 25),

            Map.entry(32, 26),
            Map.entry(18, 27),
            Map.entry(4, 28),

            Map.entry(33, 10),
            Map.entry(19, 11),
            Map.entry(5, 12),

            Map.entry(34, 13),
            Map.entry(20, 14),
            Map.entry(6, 15),

            Map.entry(35, 16),
            Map.entry(21, 17),
            Map.entry(7, 18),

            Map.entry(36, 0),
            Map.entry(22, 1),
            Map.entry(8, 2),

            Map.entry(27, 3),
            Map.entry(23, 4),
            Map.entry(9, 5),

            Map.entry(38, 6),
            Map.entry(24, 7),
            Map.entry(10, 8)
    );

    public static URL getResource(final String path) {
        return Objects.requireNonNull(Settings.class.getResource(path));
    }

    public static ImageIcon getGridLayoutImage(final String nodeName) {
        return new ImageIcon(Settings.getResource("/grids/gridlayout-" + nodeName + ".png"));
    }
}
