package brickBreak;
import javax.swing.JFrame;

public class main {

	/**
	 * First thing to open when you start the program
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame obj = new JFrame();
		GamePlay game = new GamePlay();
		obj.setBounds(100,100,700,600);
		obj.setTitle("Break it");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(game);
	}

}
