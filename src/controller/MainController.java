package controller;

import model.domain.Model;
import view.FrameManager;
import view.MainFrame;

public class MainController {

	private Model model;
	private MainFrame view;
	private FrameManager fm;

	public MainController(Model model, MainFrame view, FrameManager fm) {
		this.model = model;
		this.view = view;
		this.fm = fm;
		
		SessionManager session = new SessionManager(null, this.fm, this.model);
		session.loginPage();
	}

}
