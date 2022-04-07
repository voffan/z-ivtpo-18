package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginView extends JPanel {
    private JPanel viewPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView(App app) {
        setLayout(new BorderLayout());
        add(viewPanel, BorderLayout.CENTER);

        loginButton.addActionListener((ev) -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            Thread thread = new Thread(() -> {
                app.getDatabase().login(username, password);
                Arrays.fill(password, '\0');
            });

            thread.start();
        });
    }
}
