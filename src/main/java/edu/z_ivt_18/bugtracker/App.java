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
        // Самое главное: темы.
        // Если системная тема — Metal, то меняем её на Nimbus.
        // В противном случае (Windows) оставляем как есть.
        try {
            String systemThemeName = UIManager.getSystemLookAndFeelClassName();

            if (systemThemeName.endsWith("MetalLookAndFeel")) {
                UIManager.LookAndFeelInfo[] themes = UIManager.getInstalledLookAndFeels();

                for (UIManager.LookAndFeelInfo theme : themes) {
                    if (theme.getName().equals("Nimbus")) {
                        UIManager.setLookAndFeel(theme.getClassName());
                        break;
                    }
                }
            } else {
                UIManager.setLookAndFeel(systemThemeName);
            }
        } catch (Exception ex) {
            System.err.println("Failed to set look and feel: " + ex.getMessage());
        }

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
