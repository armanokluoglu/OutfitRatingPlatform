package view;

import javax.swing.JFrame;
import model.domain.Model;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -2898943439738426049L;
	private Model model;
	private FrameManager fm;
	
	public MainFrame(Model model, FrameManager fm) {
		super("Outfit Rating MVC");
		this.fm = fm;
		this.model = model;
        LoginFrame login = new LoginFrame(this.model, this.fm);
	}
}
