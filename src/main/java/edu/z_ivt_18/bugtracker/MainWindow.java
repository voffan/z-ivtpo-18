package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.awt.*;

public final class MainWindow extends JFrame {
    private final App app;
    private final MenuBar menuBar;

    public MainWindow(App app) {
        super(app.getTranslations().getString("title"));

        this.app = app;

        getContentPane().add(new DisconnectedView(), BorderLayout.CENTER);

        setSize(1000, 750);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menuBar = new MenuBar(app);
        setJMenuBar(menuBar);
    }

    public void openDatabaseConnectionDialog() {
        JDialog dialog = new DatabaseConnectionDialog(app);
        dialog.setVisible(true);
    }

    public void openDatabaseDisconnectionDialog() {
        if (!app.getDatabase().isConnected()) {
            showInformationMessage("You're not currently connected to the database.");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
            app.getTranslations().getString("databaseDisconnectionConfirmation"),
            null,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            app.getDatabase().disconnect();
        }
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message,
            app.getTranslations().getString("errorTitle"),
            JOptionPane.ERROR_MESSAGE);
    }

    public void showInformationMessage(String message) {
        JOptionPane.showMessageDialog(this, message,
            app.getTranslations().getString("informationTitle"),
            JOptionPane.INFORMATION_MESSAGE);
    }

    public MenuBar getMyMenuBar() {
        return menuBar;
    }
}
