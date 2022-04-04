package edu.z_ivt_18.bugtracker;

import javax.swing.*;

public final class MainWindow extends JFrame {
    private final App app;
    private final MenuBar menuBar;

    public MainWindow(App app) {
        super("Our Bug Tracker");
        this.app = app;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);

        menuBar = new MenuBar(app);
        setJMenuBar(menuBar);
    }

    public void databaseConnected() {
        menuBar.databaseConnected();
    }

    public void databaseDisconnected() {
        menuBar.databaseDisconnected();
    }
}
