package Game;

import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;

class MyCanvas extends JComponent {

	public void paint(Graphics g) {
		int rowNum = 1;
		int colNum = 1;
		int posX = 50;
		int posY = 50;

		while (rowNum <= 9) {

			g.setColor(Color.red);
			g.fillRect(posX, posY, 50, 50);
			colNum++;
			posX = posX + 60;

			if (colNum == 10) {
				colNum = 1;
				rowNum++;
				posY = posY + 60;
				posX = 50;

			}
		}
	}
}


public class GameView {
	public static void main(String[] a) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 800, 800);
		window.getContentPane().add(new MyCanvas());
		window.setVisible(true);
	}
}