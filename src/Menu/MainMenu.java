package Menu;

import javax.swing.*;
import java.awt.*;

public class MainMenu {

    public static void main(String[] args) {
        new MainMenu();
    }

    public MainMenu() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                frame.setTitle("Quoridor");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new MenuPanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    protected class MenuPanel extends JPanel {

        public MenuPanel() {
           // JLabel label = new JLabel("Quoridor");
            setLayout(new GridBagLayout());
            setPreferredSize(new Dimension(800,600));
            setMinimumSize(getPreferredSize());
            
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
           // add(label, constraints);

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridy++;
            add(new JButton("Start Game"), constraints);
            constraints.gridy++;
            
            add(new JButton("Options"), constraints);
            constraints.gridy++;
            add(new JButton("Quit"), constraints);
        }
    }
}
