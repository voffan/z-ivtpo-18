import javax.swing.*;

public class GUIFrame extends JFrame{
    private JPanel rootPanel;
    private JTextField textField1;

    public GUIFrame() {
        setContentPane(rootPanel);
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new GUIFrame();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
