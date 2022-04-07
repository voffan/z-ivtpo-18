package edu.z_ivt_18.bugtracker;

import java.awt.*;
import java.sql.*;

// Так как работа с БД будет проводиться в отдельных потоках
// (отличных от потока, на которых работает GUI), то все операции
// с GUI следует обрамлять в EventQueue.invokeLater.

public final class Database {
    private final App app;
    private Connection connection;

    public Database(App app) {
        this.app = app;
    }

    public void connect(String url, String username, char[] password) {
        String connectionUrl = "jdbc:mariadb://" + url + "/bug_tracker";

        try {
            connection = DriverManager.getConnection(connectionUrl, username, String.valueOf(password));
        } catch (SQLException ex) {
            EventQueue.invokeLater(() -> {
                String fmt = app.getTranslations().getString("databaseConnectionError");
                app.getMainWindow().showErrorMessage(String.format(fmt, ex.getMessage()));
            });
        }

        if (isConnected()) {
            EventQueue.invokeLater(() -> {
                app.getMainWindow().getMyMenuBar().getDatabaseDisconnectItem().setEnabled(true);
                app.getMainWindow().switchView(MainWindow.VIEW_LOGIN);
            });
        }
    }

    public void disconnect() {
        if (!isConnected()) {
            return;
        }

        try {
            connection.close();
            EventQueue.invokeLater(() -> {
                app.getMainWindow().getMyMenuBar().getDatabaseDisconnectItem().setEnabled(false);
                app.getMainWindow().switchView(MainWindow.VIEW_DISCONNECTED);
            });
        } catch (SQLException ex) {
            EventQueue.invokeLater(() -> {
                app.getMainWindow().showErrorMessage("Failed to disconnect from DB: " + ex.getMessage());
            });
        }
    }

    public boolean isConnected() {
        if (connection == null) {
            return false;
        }

        try {
            return !connection.isClosed();
        } catch (SQLException ex) {
            EventQueue.invokeLater(() -> {
                app.getMainWindow().showErrorMessage("Database error: " + ex.getMessage());
            });
        }

        return true;
    }

    public void login(String username, char[] password) {
        if (!isConnected()) {
            EventQueue.invokeLater(() -> {
                app.getMainWindow().showErrorMessage("Can't login, not connected to database.");
            });

            return;
        }

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(String.join("\n",
                "SELECT * FROM `User`",
                "WHERE `Login` = \"" + username + "\""
            ));

            if (!rs.first()) {
                EventQueue.invokeLater(() -> {
                    app.getMainWindow().showErrorMessage("User " + username + " is not found.");
                });
                return;
            }

            // Пароль открытым текстом это зло, надо с этим что-то делать.
            if (!rs.getString("Pwd").equals(String.valueOf(password))) {
                EventQueue.invokeLater(() -> {
                    app.getMainWindow().showErrorMessage("Incorrect password.");
                });
                return;
            }

            st.close();

            EventQueue.invokeLater(() -> {
                app.getMainWindow().switchView(MainWindow.VIEW_TRACKER);
            });
        } catch (SQLException ex) {
            EventQueue.invokeLater(() -> {
                app.getMainWindow().showErrorMessage("SQL error: " + ex.getMessage());
            });
        }
    }
}
