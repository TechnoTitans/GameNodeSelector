package src;

import edu.wpi.first.math.WPIMathJNI;
import edu.wpi.first.networktables.*;
import edu.wpi.first.util.CombinedRuntimeLoader;
import edu.wpi.first.util.WPIUtilJNI;
import src.config.Settings;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NTListener implements AutoCloseable {
    static {
        NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
        WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
        WPIMathJNI.Helper.setExtractOnStaticLoad(false);

        try {
            CombinedRuntimeLoader.loadLibraries(
                    NTListener.class,
                    "wpiutiljni",
                    "wpimathjni",
                    "ntcorejni"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private final NetworkTableInstance networkTableInstance;
    private final IntegerPublisher nodePublisher;
    private final StringSubscriber selectedNodeSubscriber;

    private final StringArraySubscriber autoSubscriber;
    private final StringPublisher autoPublisher;

    private final StringArraySubscriber profileSubscriber;
    private final StringPublisher profilePublisher;

    public NTListener() {
        this.networkTableInstance = NetworkTableInstance.getDefault();
        this.networkTableInstance.startClient4(Settings.clientName);
        this.networkTableInstance.setServer(Settings.hostName); // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
        this.networkTableInstance.startDSClient();

        final NetworkTable ntNodeTable = this.networkTableInstance.getTable(Settings.nodeNetworkTable);
        this.nodePublisher = ntNodeTable
                .getIntegerTopic(Settings.nodePublishTopic)
                .publish();

        this.selectedNodeSubscriber = ntNodeTable
                .getStringTopic(Settings.selectedAutoSubscriberTopic)
                .subscribe("");


        final NetworkTable ntAutoTable = this.networkTableInstance.getTable(Settings.autoNetworkTable);
        this.autoSubscriber = ntAutoTable.getStringArrayTopic(Settings.autoSubscriberTopic)
                .subscribe(new String[] {""});

        this.autoPublisher = ntAutoTable.getStringTopic(Settings.autoPublishTopic)
                .publish();


        final NetworkTable ntProfileTable = this.networkTableInstance.getTable(Settings.profileNetworkTable);
        this.profileSubscriber = ntProfileTable.getStringArrayTopic(Settings.profileSubscriberTopic)
                .subscribe(new String[] {""});

        this.profilePublisher = ntProfileTable.getStringTopic(Settings.profilePublishTopic)
                .publish();
    }

    public NetworkTableInstance getNetworkTableInstance() {
        return networkTableInstance;
    }

    public StringArraySubscriber getAutoSubscriber() {
        return autoSubscriber;
    }

    public StringArraySubscriber getProfileSubscriber() {
        return profileSubscriber;
    }

    public void publish(final int node) {
        nodePublisher.set(node);
    }

    public List<String> getAutoPaths() {
        return Collections.unmodifiableList(Arrays.asList(autoSubscriber.get()));
    }

    public void selectAuto(final String autoPath) {
        autoPublisher.set(autoPath);
    }

    public StringSubscriber getSelectedNodeSubscriber() {
        return selectedNodeSubscriber;
    }

    public String getSelectedNode() {
        return selectedNodeSubscriber.get();
    }

    public List<String> getDriverProfiles() {
        return Collections.unmodifiableList(Arrays.asList(profileSubscriber.get()));
    }

    public void selectProfile(final String profile) {
        profilePublisher.set(profile);
    }

    @Override
    public void close() {
        nodePublisher.close();
        selectedNodeSubscriber.close();

        autoSubscriber.close();
        autoPublisher.close();

        profileSubscriber.close();
        profilePublisher.close();
    }
}
