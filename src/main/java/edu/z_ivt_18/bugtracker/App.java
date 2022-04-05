package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public final class App {
    private final ResourceBundle translations;
    private final Preferences settings;
    private final MainWindow mainWindow;
    private final Database database;

    public App() {
        translations = ResourceBundle.getBundle("translations");
        settings = Preferences.userNodeForPackage(edu.z_ivt_18.bugtracker.App.class);
        mainWindow = new MainWindow(this);
        database = new Database(this);
    }

    public ResourceBundle getTranslations() {
        return translations;
    }

    public Preferences getSettings() {
        return settings;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public Database getDatabase() {
        return database;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.getMainWindow().setVisible(true);
        });
    }
}
