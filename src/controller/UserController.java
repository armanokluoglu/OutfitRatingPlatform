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
	private UserFrame view;
	private Model model;
	private User user;
	private List<Collection> collections;

	public UserController(Model model, UserFrame view, SessionManager session, User user) {
		this.model = model;
		this.session = session;
		this.view = view;
		this.user = user;
		this.collections = user.getCollections();
		
		view.addSubject(model);
		model.register(view);
		
		view.setCards();
		setSidebarListeners();
		setContentListeners();
	}
	
	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenUserListener());
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenOutfitsActionListener(new OpenOutfitsListener());
		view.addHomeActionListener(new OpenHomeListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}
	
	private void setContentListeners() {
		List<Collection> collections = user.getCollections();
		for (Collection collection : collections) {
			view.addOpenCollectionActionListener(new OpenCollectionListener(collection), collection.getName());
		}
		view.addCreateCollectionActionListener(new CreateCollectionListener());
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
    
    class CreateCollectionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	String newCollectionName = view.showInputDialog("Enter the name of the collection:");
        	if (newCollectionName == null || newCollectionName == "")  {
        		return;
        	}
        	newCollectionName = newCollectionName.trim();
        	model.createCollectionForUser(newCollectionName, session.getCurrentUser());
        	for (Collection collection : collections) {
    			view.addOpenCollectionActionListener(new OpenCollectionListener(collection), collection.getName());
    		}
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
