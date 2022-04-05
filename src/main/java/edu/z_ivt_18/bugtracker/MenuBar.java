package edu.z_ivt_18.bugtracker;

import javax.swing.*;

public final class MenuBar extends JMenuBar {
    private final App app;

    private JMenu databaseMenu;
    private JMenuItem databaseConnectItem;
    private JMenuItem databaseDisconnectItem;
    private JMenuItem databaseCloseItem;

    private JMenu helpMenu;
    private JMenuItem helpAboutItem;

    public MenuBar(App app) {
        super();

        this.app = app;

        buildDatabaseMenu();
        buildHelpMenu();
    }

    public JMenuItem getDatabaseDisconnectItem() {
        return databaseDisconnectItem;
    }

    private void buildDatabaseMenu() {
        // Database -> Connect...
        databaseConnectItem = new JMenuItem(app.getTranslations().getString("menu.database.connect"));
        databaseConnectItem.addActionListener((ev) -> {
            app.getMainWindow().openDatabaseConnectionDialog();
        });

        // Database -> Disconnect
        databaseDisconnectItem = new JMenuItem(app.getTranslations().getString("menu.database.disconnect"));
        databaseDisconnectItem.setEnabled(false);
        databaseDisconnectItem.addActionListener((ev) -> {
            app.getMainWindow().openDatabaseDisconnectionDialog();
        });

        // Database -> Close
        databaseCloseItem = new JMenuItem(app.getTranslations().getString("menu.database.close"));
        databaseCloseItem.addActionListener((ev) -> {
            Utils.closeWindow(app.getMainWindow());
        });

        databaseMenu = new JMenu(app.getTranslations().getString("menu.database"));
        databaseMenu.add(databaseConnectItem);
        databaseMenu.add(databaseDisconnectItem);
        databaseMenu.addSeparator();
        databaseMenu.add(databaseCloseItem);
        add(databaseMenu);
    }

    private void buildHelpMenu() {
        // Help -> About
        helpAboutItem = new JMenuItem(app.getTranslations().getString("menu.help.about"));
        helpAboutItem.addActionListener((ev) -> {
            app.getMainWindow().showInformationMessage("Under construction.\nNothing to see here.");
        });

        helpMenu = new JMenu(app.getTranslations().getString("menu.help"));
        helpMenu.add(helpAboutItem);
        add(helpMenu);
    }
}
