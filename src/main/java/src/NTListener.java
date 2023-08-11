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

public class NTListener {
    private final NetworkTableInstance networkTableInstance;
    private final IntegerPublisher nodePublisher;
    private final StringSubscriber selectedNodeSubscriber;

    private final StringArraySubscriber autoSubscriber;
    private final StringPublisher autoPublisher;

    private final StringArraySubscriber profileSubscriber;
    private final StringPublisher profilePublisher;

    public NTListener(final Settings ntSettings) throws IOException {
        synchronized (NetworkTableInstance.class) {
            NetworkTablesJNI.Helper.setExtractOnStaticLoad(false);
            WPIUtilJNI.Helper.setExtractOnStaticLoad(false);
            WPIMathJNI.Helper.setExtractOnStaticLoad(false);

            CombinedRuntimeLoader.loadLibraries(
                    NTListener.class,
                    "wpiutiljni",
                    "wpimathjni",
                    "ntcorejni"
            );

            this.networkTableInstance = NetworkTableInstance.getDefault();
            this.networkTableInstance.startClient4(ntSettings.getClientName());
            this.networkTableInstance.setServer(ntSettings.getHostname()); // where TEAM=190, 294, etc, or use inst.setServer("hostname") or similar
            this.networkTableInstance.startDSClient();

            final NetworkTable ntNodeTable = this.networkTableInstance.getTable(ntSettings.getNodeNetworkTable());
            this.nodePublisher = ntNodeTable
                    .getIntegerTopic(ntSettings.getNodePublishTopic())
                    .publish();

            this.selectedNodeSubscriber = ntNodeTable
                    .getStringTopic(ntSettings.getSelectedAutoSubscriberTopic())
                    .subscribe("");


            final NetworkTable ntAutoTable = this.networkTableInstance.getTable(ntSettings.getAutoNetworkTable());
            this.autoSubscriber = ntAutoTable.getStringArrayTopic(ntSettings.getAutoSubscriberTopic())
                    .subscribe(new String[] {""});

            this.autoPublisher = ntAutoTable.getStringTopic(ntSettings.getAutoPublishTopic())
                    .publish();


            final NetworkTable ntProfileTable = this.networkTableInstance.getTable(ntSettings.getProfileNetworkTable());
            this.profileSubscriber = ntProfileTable.getStringArrayTopic(ntSettings.getProfileSubscriberTopic())
                    .subscribe(new String[] {""});

            this.profilePublisher = ntProfileTable.getStringTopic(ntSettings.getProfilePublishTopic())
                    .publish();
        }
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
}
