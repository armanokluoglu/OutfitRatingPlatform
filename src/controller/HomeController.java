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
	private HomeFrame view;
	private List<Collection> collections;

	public HomeController(Model model, HomeFrame view, SessionManager session) {
		this.session = session;
		this.view = view;
		
		view.addSubject(model);
		model.register(view);
		
		this.collections = model.getCollectionsOfFollowingsOfUserChronologically(session.getCurrentUser());
		view.setCards();
		
		setSidebarListeners();
		setContentListeners();
	}
	
	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addOutfitsActionListener(new OpenOutfitsListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}
	
	private void setContentListeners() {
		for (Collection collection : collections) {
			view.addOpenCollectionActionListener(new OpenCollectionListener(collection), collection.getName());
			view.addOpenUserActionListener(new OpenUserListener(collection.getCreator()), collection.getCreator().getUsername());
		}
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
