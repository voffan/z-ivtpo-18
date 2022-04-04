package edu.z_ivt_18.bugtracker;

import javax.swing.*;

public final class MenuBar extends JMenuBar {
    private final App app;

    private JMenuItem fileDisconnectItem;

    public MenuBar(App app) {
        super();
        this.app = app;

        add(createFileMenu());
    }

    public void databaseConnected() {
        if (fileDisconnectItem != null) {
            fileDisconnectItem.setEnabled(true);
        }
    }

    public void databaseDisconnected() {
        if (fileDisconnectItem != null) {
            fileDisconnectItem.setEnabled(false);
        }
    }

    private JMenu createFileMenu() {
        JMenuItem connectItem = new JMenuItem("Connect to database...");
        connectItem.addActionListener((ev) -> { app.openConnectDialog(); });

        JMenuItem disconnectItem = new JMenuItem("Disconnect from database");
        disconnectItem.setEnabled(false);
        disconnectItem.addActionListener((ev) -> { app.openDisconnectDialog(); });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener((ev) -> { app.exit(); });

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(connectItem);
        fileMenu.add(disconnectItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        fileDisconnectItem = disconnectItem;

        return fileMenu;
    }
}
