package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class ConnectionDialog extends JDialog {
    private final App app;
    private final JTextField urlField;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public ConnectionDialog(App app) {
        super(app.getMainWindow(), "Database connection");
        this.app = app;

        setSize(300, 400);
        setLocationRelativeTo(app.getMainWindow());

        urlField = new JTextField("localhost");
        usernameField = new JTextField("root");
        passwordField = new JPasswordField();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        createTop(panel);
        createBottom(panel);

        setContentPane(panel);
    }

    private void createTop(JPanel panel) {
        Container topPane = new Container();
        topPane.setLayout(new GridLayout(3, 2));

        topPane.add(new JLabel("URL:"));
        topPane.add(urlField);
        topPane.add(new JLabel("Username:"));
        topPane.add(usernameField);
        topPane.add(new JLabel("Password:"));
        topPane.add(passwordField);

        panel.add(topPane, BorderLayout.PAGE_START);
    }

    private void createBottom(JPanel panel) {
        Container bottomPane = new Container();
        bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.X_AXIS));

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener((ev) -> {
            String url = urlField.getText();
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            if (url.isEmpty()) {
                JOptionPane.showMessageDialog(app.getMainWindow(), "IP address should be filled.");
                return;
            }

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(app.getMainWindow(), "Username should be filled.");
                return;
            }

            boolean connected = app.connectToDB(url, username, password);
            Arrays.fill(password, '\0');

            if (connected) {
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ev) -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        bottomPane.add(connectButton);
        bottomPane.add(cancelButton);

        panel.add(bottomPane, BorderLayout.PAGE_END);
    }
}
