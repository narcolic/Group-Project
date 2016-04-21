package Game;

import java.awt.Graphics;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;

class MyCanvas extends JComponent {

	public void paint(Graphics g) {
		int rowNum = 1;
		int colNum = 1;
		int posX = 130;
		int posY = 40;
		
		//A rectangle to contain the player wall count
		g.drawRect (750, 40, 300, 320);
		
		// Each player's wall count container
		g.drawRect(755, 45, 290, 70 );
		g.drawRect(755, 125, 290, 70 );
		g.drawRect(755, 205, 290, 70 );
		g.drawRect(755, 285, 290, 70 );
		
		while (rowNum <= 9) {

			g.setColor(Color.red);
			g.fillRect(posX, posY, 50, 50);
			colNum++;
			posX = posX + 55;

			if (colNum == 10) {
				colNum = 1;
				rowNum++;
				posY = posY + 55;
				posX = 130;

			}
		}
		
	}
}

public class GameView {
	public static void main(String[] a) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 1080, 580);
		window.getContentPane().add(new MyCanvas());
		window.setVisible(true);
	}
}
