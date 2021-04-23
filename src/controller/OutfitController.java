package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.domain.Collection;
import model.domain.Model;
import model.domain.Outfit;
import view.HomeFrame;
import view.OutfitFrame;

public class OutfitController {

	private SessionManager session;
	private Model model;
	private Outfit outfit;
	private OutfitFrame view;

	public OutfitController(Model model, OutfitFrame view, SessionManager session, Outfit outfit) {
		this.model = model;
		this.view = view;
		this.outfit = outfit;
		this.session = session;
		
		view.addSubject(model);
		
	}
	
    class OpenUserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.loginPage();
        }
    }
    
    class OpenCollectionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.loginPage();
        }
    }
}
