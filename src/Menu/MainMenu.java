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
            JLabel label = new JLabel("Quoridor");
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(label, gbc);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridy++;
            add(new JButton("Start Game"), gbc);
            gbc.gridy++;
            add(new JButton("Options"), gbc);
            gbc.gridy++;
            add(new JButton("Quit"), gbc);
        }
    }
}