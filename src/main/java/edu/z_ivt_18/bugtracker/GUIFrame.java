package edu.z_ivt_18.bugtracker;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.util.Scanner;


public class GUIFrame extends JFrame {
    public JPanel rootPanel;
    public JTextField TextField;
    public JButton Button_add;
    public JButton Button_attach;


    public GUIFrame() {
        getContentPane().add(rootPanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setTitle("Добавить комментарий");
        pack();
        setLocationRelativeTo(null);

        //Добавление комментария
        Button_add.addActionListener(actionEvent -> {

            //String x;

            try{
                String url = "jdbc:mysql://localhost:3306/errors";
                String username = "root";
                String password = "1234";
                //Scanner scanner = new Scanner(System.in);

                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

                String x = String.valueOf(TextField.getText());
                //x = scanner.nextLine();


                try (Connection conn = DriverManager.getConnection(url, username, password)){
                    String sql = "INSERT comment (Descr, IsSolution, ErrorId) VALUES (?, 1, 1)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, x);

                    int rows = preparedStatement.executeUpdate();

                    System.out.printf("Added %d rows", rows);
                }
            }
            catch(Exception ex) {
                System.out.println("Connection failed...");

                System.out.println(ex);
            }
        });
        Button_add.addActionListener(actionEvent -> dispose());

    }
    public static void main(String[] args) {
        // Создание и открытие окна
        SwingUtilities.invokeLater(() -> new GUIFrame().setVisible(true));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
