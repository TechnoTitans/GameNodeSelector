package src.ui;

import edu.wpi.first.networktables.NetworkTableEvent;
import src.NTListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EnumSet;

public class UI extends JFrame {
    private final NTListener ntListener;

    private JComboBox<String> autoSelector;
    private JComboBox<String> profileSelector;
    private JLabel autonomousLabel;
    private JLabel driverLabel;
    private JMenu jMenu1;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;

    public UI(final NTListener ntListener) {
        this.ntListener = ntListener;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            initComponents();
            setVisible(true);

            ntListener.getNetworkTableInstance().addListener(
                    ntListener.getAutoSubscriber(),
                    EnumSet.of(NetworkTableEvent.Kind.kPublish),
                    (event) -> populateAutonomousPaths()
            );
        });
    }

    private void initComponents() {
        autoSelector = new JComboBox<>();
        profileSelector = new JComboBox<>();
        autonomousLabel = new JLabel();
        driverLabel = new JLabel();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMenuItem1 = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("TitanDash");

        autoSelector.setToolTipText("Select Autonomous");
        autoSelector.addActionListener(this::autoSelectedEvent);
        populateAutonomousPaths();

        profileSelector.setToolTipText("Select Driver Profile");
        profileSelector.addActionListener(this::profileSelectedEvent);
        populateDriverProfiles();

        autonomousLabel.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        autonomousLabel.setHorizontalAlignment(SwingConstants.CENTER);
        autonomousLabel.setText("Autonomous");

        driverLabel.setFont(new java.awt.Font("Segoe UI", Font.PLAIN, 14));
        driverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        driverLabel.setText("Driver Profile");

        jMenu1.setText("File");

        jMenuItem1.setText("Refresh");
        jMenuItem1.addActionListener(this::refreshButtonPressed);
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        final GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(autoSelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(122, 122, 122)
                                .addComponent(profileSelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(50, 50, 50))
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(140, Short.MAX_VALUE)
                                .addComponent(autonomousLabel, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 300, Short.MAX_VALUE)
                                .addComponent(driverLabel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(140, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(autonomousLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(driverLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(autoSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(profileSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(432, Short.MAX_VALUE))
        );

        pack();
    }

    //Listeners
    private void refreshButtonPressed(final ActionEvent evt) {
        populateAutonomousPaths();
        populateDriverProfiles();
    }

    private void autoSelectedEvent(final ActionEvent evt) {
        final String selectedAuto = (String) autoSelector.getSelectedItem();
        if (selectedAuto != null) {
            ntListener.selectAuto(selectedAuto);
        }
    }

    private void profileSelectedEvent(final ActionEvent evt) {
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
