package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;

public class UsersView extends JPanel {
    private final App app;

    private JPanel viewPanel;
    private JTable userListTable;
    private JPanel addNewUserPanel;

    private JTextField newUsernameField;
    private JTextField newFullNameField;
    private JPasswordField newPasswordField;
    private JButton newAddButton;

    private ButtonGroup editorButtonGroup;
    private JToggleButton addNewUserToggleButton;
    private JToggleButton editUserToggleButton;

    public UsersView(App app) {
        this.app = app;

        setLayout(new BorderLayout());
        add(viewPanel, BorderLayout.CENTER);

        userListTable.setModel(new UserListTableModel());
        userListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addNewUserToggleButton.addItemListener((ev) -> {
            switch (ev.getStateChange()) {
                case ItemEvent.SELECTED:
                    addNewUserPanel.setVisible(true);
                    break;
                case ItemEvent.DESELECTED:
                    addNewUserPanel.setVisible(false);
                    break;
            }
        });

        editUserToggleButton.setEnabled(false); // временно отключена

        newAddButton.addActionListener((ev) -> {
            String username = newUsernameField.getText();

            if (username.isEmpty()) {
                app.getMainWindow().showErrorMessage("Username is empty");
                return;
            }

            if (username.split(" ").length > 1) {
                app.getMainWindow().showErrorMessage("Username should not contain spaces.");
                return;
            }

            String fullName = newFullNameField.getText();
            char[] password = newPasswordField.getPassword();

            if (password.length == 0) {
                app.getMainWindow().showErrorMessage("Password is empty");
                return;
            }

            Thread thread = new Thread(() -> {
                app.getDatabase().addUser(new User(username, fullName, password));
                Arrays.fill(password, '\0');
                EventQueue.invokeLater(this::reload);
            });

            thread.start();
        });
    }

    public void reload() {
        UserListTableModel userListTableModel = (UserListTableModel) userListTable.getModel();
        userListTableModel.setRowCount(0);
        for (User user : app.getDatabase().getUsers()) {
            userListTableModel.addRow(user);
        }

        addNewUserPanel.setVisible(false);
        editorButtonGroup.clearSelection();

        newUsernameField.setText("");
        newFullNameField.setText("");
        newPasswordField.setText("");
    }

    private static class UserListTableModel extends DefaultTableModel {
        public UserListTableModel() {
            addColumn("#");
            addColumn("Username");
            addColumn("Full name");
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public void addRow(User user) {
            addRow(new Object[]{ user.getId(), user.getUsername(), user.getFullName() });
        }
    }
}
