package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.sql.*;

public final class Database {
    private final App app;
    private Connection connection;

    public Database(App app) {
        this.app = app;
    }

    public void connect(String url, String username, char[] password) throws SQLException {
        String connectionUrl = "jdbc:mariadb://" + url + "/bug_tracker";
        connection = DriverManager.getConnection(connectionUrl, username, String.valueOf(password));
    }

    public void disconnect() {
        try {
            if (connection != null) {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
        } catch (SQLException ex) {
            System.err.println("Failed to disconnect from DB: " + ex.getMessage());
        }
    }

    public boolean isConnected() {
        if (connection == null) {
            return false;
        }

        try {
            return connection.isClosed() == false;
        } catch (SQLException ex) {
            
        }

        return true;
    }
}
