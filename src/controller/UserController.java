package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.domain.Collection;
import model.domain.Model;
import model.domain.User;
import view.HomeFrame;
import view.UserFrame;

public class UserController {

	private SessionManager session;
	private Model model;
	private User user;
	private UserFrame view;

	public UserController(Model model, UserFrame view, SessionManager session, User user) {
		this.model = model;
		this.view = view;
		this.user = user;
		this.session = session;
		
		view.addSubject(model);
		
		List<Collection> collections = model.getCollectionsOfFollowingsOfUserChronologically(session.getCurrentUser());
		view.setCards(collections);
		for (Collection collection : collections) {
			view.addOpenCollectionActionListener(new OpenCollectionListener(), collection.getName());
			view.addOpenUserActionListener(new OpenUserListener(), collection.getCreator().getUsername());
		}
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
