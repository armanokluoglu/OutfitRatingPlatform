package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.domain.Collection;
import model.domain.Model;
import model.domain.User;
import view.HomeFrame;

public class HomeController {

	private SessionManager session;
	private Model model;
	private HomeFrame view;

	public HomeController(Model model, HomeFrame view, SessionManager session) {
		this.model = model;
		this.view = view;
		this.session = session;
		
		view.addSubject(model);
		
		List<Collection> collections = model.getCollectionsOfFollowingsOfUserChronologically(session.getCurrentUser());
		view.setCards(collections);
		for (Collection collection : collections) {
			view.addOpenCollectionActionListener(new OpenCollectionListener(collection), collection.getName());
			view.addOpenUserActionListener(new OpenUserListener(collection.getCreator()), collection.getCreator().getUsername());
		}
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addOutfitsActionListener(new OpenOutfitsListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}
	
    class OpenUserListener implements ActionListener {
    	private User user;
    	
    	public OpenUserListener(User user) {
    		this.user = user;
    	}
    	
        public void actionPerformed(ActionEvent e) {
        	session.userPage(user);
        }
    }
    
    class OpenCollectionListener implements ActionListener {
    	private Collection collection;
    	
    	public OpenCollectionListener(Collection collection) {
    		this.collection = collection;
    	}
    	
        public void actionPerformed(ActionEvent e) {
        	session.collectionPage(collection);
        }
    }
    
    class OpenStatisticsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.statisticsPage();
        }
    }
    
    class OpenOutfitsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.outfitsPage();
        }
    }
    
    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.loginPage();
        }
    }
}
