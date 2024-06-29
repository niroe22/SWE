package artcreator;

import artcreator.gui.CreatorFrame;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Pinalizer");
		frame.setContentPane(new CreatorFrame().getContentPane());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
