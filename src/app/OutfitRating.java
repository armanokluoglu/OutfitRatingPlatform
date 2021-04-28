package app;

import controller.MainController;
import model.domain.Model;
import model.exception.UserAlreadyException;
import view.FrameManager;
import view.MainFrame;

public class OutfitRating {
	FrameManager fm;
	FrameManager fm2;

	public OutfitRating() {
		this.fm = new FrameManager();
		this.fm2 = new FrameManager();
	}
	
	public void start() throws UserAlreadyException {
		Model model = new Model();
        MainFrame view = new MainFrame(model, this.fm);
        MainController controller = new MainController(model, view, this.fm);
		MainController controller2 = new MainController(model, view, this.fm2);
	}
}