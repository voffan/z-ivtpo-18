package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class DatabaseConnectionDialog extends JDialog {
    private JPanel mainPanel;
    private JTextField urlField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton cancelButton;
    private JButton connectButton;
    private JCheckBox rememberCredentialsCheckBox;

    public DatabaseConnectionDialog(App app) {
        super(app.getMainWindow(), "Database Connection");

        setContentPane(mainPanel);
        pack();
        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(app.getMainWindow());

        setupButtons(app);

        String savedUrl = app.getSettings().get("dbUrl", "localhost");
        String savedUsername = app.getSettings().get("dbUsername", "admin");
        String savedPassword = app.getSettings().get("dbPassword", "");

        urlField.setText(savedUrl);
        usernameField.setText(savedUsername);
        passwordField.setText(new String(Base64.getDecoder().decode(savedPassword)));
    }

    private void setupButtons(App app) {
        connectButton.addActionListener((ev) -> {
            String url = urlField.getText();
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            if (url.isEmpty()) {
                app.getMainWindow().showErrorMessage("URL must be set.");
                return;
            }

            if (username.isEmpty()) {
                app.getMainWindow().showErrorMessage("Username must be set.");
                return;
            }

            if (rememberCredentialsCheckBox.isSelected()) {
                app.getSettings().put("dbUrl", url);
                app.getSettings().put("dbUsername", username);

                byte[] passwordBytes = String.valueOf(password).getBytes(StandardCharsets.UTF_8);
                app.getSettings().put("dbPassword", Base64.getEncoder().encodeToString(passwordBytes));

                Arrays.fill(passwordBytes, (byte)0);
            }

            // Попытаться подключиться к БД, и закрыть это окно, если получилось
            app.getDatabase().connect(url, username, password);
            Arrays.fill(password, '\0');

            if (app.getDatabase().isConnected()) {
                Utils.closeWindow(this);
            }
        });

        cancelButton.addActionListener((ev) -> {
            Utils.closeWindow(this);
        });
    }
}
