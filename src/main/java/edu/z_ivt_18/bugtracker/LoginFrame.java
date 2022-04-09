package edu.z_ivt_18.bugtracker;



import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginFrame extends JFrame implements ActionListener {

    Container container=getContentPane();
    JLabel userLabel = new JLabel("USERMANE");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("SHOW PASSWORD");

    LoginFrame () {
        setLayoutManeger();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManeger () {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        userLabel.setBounds(50,150,100,30);
        passwordLabel.setBounds(50,220,100,30);
        userTextField.setBounds(150,150,150,30);
        passwordField.setBounds(150,220,150,30);
        showPassword.setBounds(150,250,150,30);
        loginButton.setBounds(50,300,100,30);
        resetButton.setBounds(200,300,100,30);
    }

    public void addComponentsToContainer ()
    {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvent()
    {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e)  {

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Connection connection;
                    String dbuser = "root";
                    String dbpassw = "1234";
                    String databaseName = "errors";
                    String url = "jdbc:mysql://localhost/" + databaseName;
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(url, dbuser, dbpassw);
                    Statement st = connection.createStatement();
                    ResultSet res = st.executeQuery("SELECT * FROM user WHERE Login='" + userTextField.getText() + "' AND Pwd='" + passwordField.getText() + "'");
                    if (res.next()) {
                        JOptionPane.showMessageDialog(null, "Uspesho");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nea");
                    }
                } catch (Exception f) {
                    f.printStackTrace();
                }


            }


        });


    }

}




       /*if(e.getSource()==loginButton)
        {
            String userText;
            String passwordText;
            userText=userTextField.getText();
            passwordText=passwordField.getText();
            if(userText.equalsIgnoreCase("savvich")&&passwordText.equalsIgnoreCase("12345"))
            {
                JOptionPane.showMessageDialog(this, "Login Successfull");
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Invalid Username or Password");
            }

        }

        }*/
