package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class TrackerView extends JPanel {
    private JPanel viewPanel;
    private JComboBox<String> cardComboBox;
    private JPanel content;

    private final ReportsView reportsView;
    private final UsersView usersView;

    private static final String VIEW_REPORTS = "reports";
    private static final String VIEW_USERS = "users";

    public TrackerView(App app) {
        setLayout(new BorderLayout());
        add(viewPanel, BorderLayout.CENTER);

        String[] cardNames = { "reports", "users" };

        for (String item : cardNames) {
            cardComboBox.addItem(item);
        }

        reportsView = new ReportsView();
        usersView = new UsersView(app);

        content.add(reportsView, VIEW_REPORTS);
        content.add(usersView, VIEW_USERS);

        cardComboBox.addItemListener((ev) -> {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                String view = (String) ev.getItem();

                if (view.equals(VIEW_USERS)) {
                    usersView.reload();
                }

                CardLayout layout = (CardLayout) content.getLayout();
                layout.show(content, view);
            }
        });
    }
}
