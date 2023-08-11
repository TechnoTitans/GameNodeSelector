package src.ui;

import edu.wpi.first.networktables.NetworkTableEvent;
import src.NTListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EnumSet;
import java.util.Objects;

public class UI extends JFrame {
    private final NTListener ntListener;

    private JComboBox<String> autoSelector;
    private JLabel autonomousLabel;
    private JMenu fileMenu;
    private JLabel gridLayout;
    private JMenuBar menuBar;
    private JLabel profileLabel;
    private JComboBox<String> profileSelector;
    private JMenuItem refreshMenuItem;

    public UI(final NTListener ntListener) {
        this.ntListener = ntListener;

        /* Create and display the form */
        EventQueue.invokeLater(() -> {
            initComponents();
            setVisible(true);
            ntEventListeners();
        });
    }

    private void ntEventListeners() {
        ntListener.getNetworkTableInstance().addListener(
                ntListener.getAutoSubscriber(),
                EnumSet.of(NetworkTableEvent.Kind.kTopic),
                (event) -> populateAutonomousPaths()
        );

        ntListener.getNetworkTableInstance().addListener(
                ntListener.getProfileSubscriber(),
                EnumSet.of(NetworkTableEvent.Kind.kTopic),
                (event) -> populateDriverProfiles()
        );

        ntListener.getNetworkTableInstance().addListener(
                ntListener.getSelectedNodeSubscriber(),
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                (event) -> gridLayout.setIcon(
                        new ImageIcon(Objects.requireNonNull(
                                getClass().getResource(
                                        "/grids/gridlayout-" + ntListener.getSelectedNode() + ".png"
                                ))
                        )
                )
        );

        ntListener.getNetworkTableInstance().addConnectionListener(
                true,
                (event) -> setTitle("TitanDash | " +
                        (event.is(NetworkTableEvent.Kind.kConnected)
                                ? "Connected"
                                : event.is(NetworkTableEvent.Kind.kDisconnected)
                                ? "Disconnected"
                                : "Unknown"
                        )
                )
        );
    }

    private void initComponents() {
        autoSelector = new JComboBox<>();
        profileSelector = new JComboBox<>();
        autonomousLabel = new JLabel();
        profileLabel = new JLabel();
        gridLayout = new JLabel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        refreshMenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("TitanDash | Disconnected");
        setMinimumSize(new Dimension(800, 450));
        setPreferredSize(new Dimension(800, 450));

        autoSelector.addActionListener(this::autoSelectorActionPerformed);
        populateAutonomousPaths();

        profileSelector.addActionListener(this::profileSelectorActionPerformed);
        populateDriverProfiles();

        autonomousLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        autonomousLabel.setHorizontalAlignment(SwingConstants.CENTER);
        autonomousLabel.setText("Autonomous");

        profileLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        profileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileLabel.setText("Profile Selector");

        gridLayout.setHorizontalAlignment(SwingConstants.CENTER);
        gridLayout.setIcon(new ImageIcon(
                Objects.requireNonNull(getClass().getResource("/grids/gridlayout-UNKNOWN.png")))
        );

        fileMenu.setText("File");

        refreshMenuItem.setText("Refresh");
        refreshMenuItem.addActionListener(evt -> refreshMenuItemActionPerformed());
        fileMenu.add(refreshMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        final GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(106, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(
                                                autoSelector,
                                                GroupLayout.PREFERRED_SIZE,
                                                287,
                                                GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                                autonomousLabel,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE
                                        ))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(
                                                profileLabel,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE
                                        )
                                        .addComponent(
                                                profileSelector,
                                                GroupLayout.PREFERRED_SIZE,
                                                287,
                                                GroupLayout.PREFERRED_SIZE
                                        ))
                                .addContainerGap(107, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(
                                        gridLayout, GroupLayout.PREFERRED_SIZE, 783, GroupLayout.PREFERRED_SIZE
                                )
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(
                                                autonomousLabel,
                                                GroupLayout.PREFERRED_SIZE,
                                                27,
                                                GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                                profileLabel,
                                                GroupLayout.PREFERRED_SIZE,
                                                27,
                                                GroupLayout.PREFERRED_SIZE
                                        ))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(
                                                autoSelector,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE
                                        )
                                        .addComponent(
                                                profileSelector,
                                                GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE
                                        ))
                                .addGap(80, 80, 80)
                                .addComponent(
                                        gridLayout, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE
                                )
                                .addContainerGap(81, Short.MAX_VALUE))
        );

        pack();
    }

    //Listeners
    private void refreshMenuItemActionPerformed() {
        populateAutonomousPaths();
        populateDriverProfiles();
    }

    private void autoSelectorActionPerformed(final ActionEvent evt) {
        final String selectedAuto = (String) autoSelector.getSelectedItem();
        if (selectedAuto != null) {
            ntListener.selectAuto(selectedAuto);
        }
    }

    private void profileSelectorActionPerformed(final ActionEvent evt) {
        final String selectedProfile = (String) profileSelector.getSelectedItem();
        if (selectedProfile != null) {
            ntListener.selectProfile(selectedProfile);
        }
    }

    private void populateAutonomousPaths() {
        autoSelector.removeAllItems();
        ntListener.getAutoPaths().forEach(autoSelector::addItem);
    }

    private void populateDriverProfiles() {
        profileSelector.removeAllItems();
        ntListener.getDriverProfiles().forEach(profileSelector::addItem);
    }
}
