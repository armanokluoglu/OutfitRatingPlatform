package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.domain.Collection;
import model.domain.Model;
import model.domain.Outfit;
import view.HomeFrame;
import view.OutfitFrame;
import view.OutfitsFrame;

public class OutfitsController {

	private SessionManager session;
	private Model model;
	private OutfitsFrame view;

	public OutfitsController(Model model, OutfitsFrame view, SessionManager session) {
		this.model = model;
		this.view = view;
		this.session = session;
		
		view.addSubject(model);
		
		List<Outfit> collections = model.getAllOutfits();
		view.setCards(collections);
//		for (Collection collection : collections) {
//			view.addOpenCollectionActionListener(new OpenCollectionListener(), collection.getName());
//			view.addOpenUserActionListener(new OpenUserListener(), collection.getCreator().getUsername());
//		}
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
