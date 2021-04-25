package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import controller.HomeController.OpenCollectionListener;
import controller.OutfitController.LogoutListener;
import controller.OutfitController.OpenHomeListener;
import controller.OutfitController.OpenOutfitsListener;
import controller.OutfitController.OpenStatisticsListener;
import controller.OutfitController.OpenUserListener;
import model.domain.Collection;
import model.domain.Model;
import model.domain.User;
import view.HomeFrame;
import view.UserFrame;

public class UserController {

	private SessionManager session;
	private Model model;

	public UserController(Model model, UserFrame view, SessionManager session, User user) {
		this.model = model;
		this.session = session;
		
		view.addSubject(model);
		model.register(view);
		
		view.setCards();
		List<Collection> collections = user.getCollections();
		for (Collection collection : collections) {
			view.addOpenCollectionActionListener(new OpenCollectionListener(collection), collection.getName());
		}
		
		view.addOpenProfileActionListener(new OpenUserListener());
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenOutfitsActionListener(new OpenOutfitsListener());
		view.addHomeActionListener(new OpenHomeListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}
	
    class OpenOutfitsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.outfitsPage();
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
	
    class OpenUserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.userPage(session.getCurrentUser());
        }
    }
    
    class OpenHomeListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		session.homePage();
    	}
    }
    
    class OpenStatisticsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.statisticsPage();
        }
    }
    
    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.loginPage();
        }
    }
}
