package app;

import javax.swing.SwingUtilities;

import view.MainFrame;

public class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainFrame::new);
	}
}
