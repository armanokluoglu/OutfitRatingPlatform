package app;

import controller.MainController;
import model.domain.Model;
import view.FrameManager;
import view.MainFrame;

public class OutfitRating {
	FrameManager fm;
	
	public OutfitRating() {
		this.fm = new FrameManager();
	}
	
	public void start() {
		Model model = new Model();
        MainFrame view = new MainFrame(model, this.fm);
        MainController controller = new MainController(model, view, this.fm);
	}
}