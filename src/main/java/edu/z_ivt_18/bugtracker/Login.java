package edu.z_ivt_18.bugtracker;

import javax.swing.*;

public class Login {
    public static void main(String[] a ) {

        LoginFrame frame=new LoginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(10,10,370,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
