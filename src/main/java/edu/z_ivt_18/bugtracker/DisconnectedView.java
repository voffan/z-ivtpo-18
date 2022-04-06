package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.awt.*;

public class DisconnectedView extends JPanel {
    private JPanel viewPanel;

    public DisconnectedView() {
        setLayout(new BorderLayout());
        add(viewPanel, BorderLayout.CENTER);
    }
}
