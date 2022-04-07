package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class TrackerView extends JPanel {
    private JPanel viewPanel;
    private JComboBox<String> cardComboBox;
    private JPanel cards;

    public TrackerView() {
        setLayout(new BorderLayout());
        add(viewPanel, BorderLayout.CENTER);

        String[] cardNames = { "reports", "users" };

        for (String item : cardNames) {
            cardComboBox.addItem(item);
        }

        cardComboBox.addItemListener((ev) -> {
            if (ev.getStateChange() == ItemEvent.SELECTED) {
                CardLayout layout = (CardLayout) cards.getLayout();
                layout.show(cards, (String) ev.getItem());
            }
        });
    }
}
