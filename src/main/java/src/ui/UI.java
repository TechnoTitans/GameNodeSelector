package src.ui;

import edu.wpi.first.networktables.NetworkTableEvent;
import src.NTListener;

import javax.swing.*;
import java.awt.*;
import java.util.EnumSet;

public class UI extends JFrame {
    private final NTListener ntListener;

    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JLabel autonomousLabel;
    private javax.swing.JComboBox<String> autoSelector;
    private javax.swing.JLabel profileLabel;
    private javax.swing.JComboBox<String> profileSelector;

    public UI(final NTListener ntListener) {
        this.ntListener = ntListener;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            initComponents();
            setVisible(true);

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
        });
    }

    private void initComponents() {
        autoSelector = new javax.swing.JComboBox<>();
        profileSelector = new javax.swing.JComboBox<>();
        autonomousLabel = new javax.swing.JLabel();
        profileLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        refreshMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TitanDashboard | Unknown");
        setMinimumSize(new java.awt.Dimension(800, 308));

        autoSelector.addActionListener(this::autoSelectorActionPerformed);
        populateAutonomousPaths();

        profileSelector.addActionListener(this::profileSelectorActionPerformed);
        populateDriverProfiles();

        autonomousLabel.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        autonomousLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        autonomousLabel.setText("Autonomous");

        profileLabel.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14)); // NOI18N
        profileLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profileLabel.setText("Profile Selector");

        fileMenu.setText("File");

        refreshMenuItem.setText("Refresh");
        refreshMenuItem.addActionListener(this::refreshMenuItemActionPerformed);
        fileMenu.add(refreshMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(106, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(autoSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(autonomousLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(profileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(profileSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(106, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(autonomousLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(profileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(autoSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(profileSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(477, Short.MAX_VALUE))
        );
        pack();
    }

    //Listeners
    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        populateAutonomousPaths();
        populateDriverProfiles();
    }

    private void autoSelectorActionPerformed(java.awt.event.ActionEvent evt) {
        final String selectedAuto = (String) autoSelector.getSelectedItem();
        if (selectedAuto != null) {
            ntListener.selectAuto(selectedAuto);
        }
    }

    private void profileSelectorActionPerformed(java.awt.event.ActionEvent evt) {
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
