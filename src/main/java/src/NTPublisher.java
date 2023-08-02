package src;

import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.util.CombinedRuntimeLoader;
import edu.wpi.first.util.WPIUtilJNI;

import java.io.IOException;

public class NTPublisher {
    private final IntegerPublisher nodePublisher;

    public NTPublisher(final String hostName) throws IOException {
        NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
        WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
        WPIMathJNI.Helper.setExtractOnStaticLoad(false);
        CameraServerJNI.Helper.setExtractOnStaticLoad(false);

        CombinedRuntimeLoader.loadLibraries(
                NTPublisher.class,
                "wpiutiljni",
                "wpimathjni",
                "ntcorejni",
                "cscorejnicvstatic"
        );

        final NetworkTableInstance networkTableInstance = NetworkTableInstance.getDefault();
        final NetworkTable table = networkTableInstance.getTable("GameNodeSelector");
        this.nodePublisher = table.getIntegerTopic("Node").publish();

        networkTableInstance.startClient4("GameNodeSelectorListener");
        networkTableInstance.setServer(hostName); // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        networkTableInstance.startDSClient();
    }

    public void publish(final int node) {
        nodePublisher.set(node);
    }
}
