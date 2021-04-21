package controller;

import model.data_access.InputOutput;
import model.data_access.OutfitRepository;
import model.data_access.UserRepository;
import model.domain.Model;
import view.MainFrame;

public class OutfitRatingController {

	private UserRepository userRepo;
	private OutfitRepository outfitRepo;
	private Model model;
	private MainFrame view;

	public OutfitRatingController(Model model, MainFrame view) {
		InputOutput io = new InputOutput();
		this.userRepo = new UserRepository(io);
		this.outfitRepo = new OutfitRepository(io);
		this.model = model;
		this.view = view;
	}
}
