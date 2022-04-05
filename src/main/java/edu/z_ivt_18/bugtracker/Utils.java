package edu.z_ivt_18.bugtracker;

import java.awt.*;
import java.awt.event.WindowEvent;

public final class Utils {
    public static void closeWindow(Window window) {
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}
