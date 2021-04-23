package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.domain.Collection;
import model.domain.Model;
import view.CollectionFrame;
import view.HomeFrame;

public class CollectionController {

	private SessionManager session;
	private Model model;
	private Collection collection;
	private CollectionFrame view;

	public CollectionController(Model model, CollectionFrame view, SessionManager session, Collection collection) {
		this.model = model;
		this.view = view;
		this.collection = collection;
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
