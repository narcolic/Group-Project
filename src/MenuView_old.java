import Menu.Help;
import Menu.Options;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuView_old {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuView_old window = new MenuView_old();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuView_old() {
		initialise();

	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialise() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Options button on Menu
		JButton btnOptions = new JButton("Options");
		btnOptions.addActionListener(new ActionListener() {
			//public void actionPerformed(ActionEvent arg0) {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false); // hide this window
				Options op = new Options();
				//op.Options(); // open Options window
			}
		});		
		btnOptions.setBounds(132, 137, 155, 46);
		frame.getContentPane().add(btnOptions);
		
		//Help button on Menu
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false); // hide this window
				Help help = new Help();
				//help.Help(); // open Help window
			}
		});
		btnHelp.setBounds(132, 80, 155, 46);
		frame.getContentPane().add(btnHelp);
		
		//Quit button on Menu
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//confirmation dialog to ask whether the user really wants to quit Quoridor
				int option = JOptionPane.showConfirmDialog(
					    frame,
					    "Are you sure you want to quit Quoridor?",
					    "Quit",
					    JOptionPane.YES_NO_OPTION);
				//if the option is to quit
				if(option == JOptionPane.YES_OPTION){
					System.exit(0); //exit System
				}
			}
		});
		btnQuit.setBounds(132, 194, 155, 46);
		frame.getContentPane().add(btnQuit);
		
		//Start button on Menu
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//NEED TO ADD ACTION
			}
		});
		btnStart.setBounds(132, 23, 155, 46);
		frame.getContentPane().add(btnStart);
	}
}
