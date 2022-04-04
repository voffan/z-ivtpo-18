package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class App {
    private final MainWindow mainWindow;
    private final Database database;

    public App() {
        mainWindow = new MainWindow(this);
        database = new Database(this);
    }

    public void execute() {
        mainWindow.setVisible(true);
    }

    public void openConnectDialog() {
        ConnectionDialog dialog = new ConnectionDialog(this);
        dialog.setVisible(true);
    }

    public void openDisconnectDialog() {
        database.disconnect();
        mainWindow.databaseDisconnected();
    }

    public void exit() {
        database.disconnect();
        mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
    }

    public JFrame getMainWindow() {
        return mainWindow;
    }

    public boolean connectToDB(String url, String username, char[] password) {
        try {
            database.connect(url, username, password);
            mainWindow.databaseConnected();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                mainWindow,
                "Failed to connect to DB: " + ex.getMessage(),
                "Database connection error",
                JOptionPane.ERROR_MESSAGE
            );

            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.execute();
        });
    }
}
