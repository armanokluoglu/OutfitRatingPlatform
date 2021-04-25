package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.Collection;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
import view.CollectionFrame;

public class CollectionController {

	private SessionManager session;

	public CollectionController(Model model, CollectionFrame view, SessionManager session, Collection collection) {
		this.session = session;
		
		view.addSubject(collection);
		collection.register(view);
		
		List<Outfit> outfits = collection.getOutfits();
		view.setCards();
		
		for (Outfit outfit : outfits) {
			view.addOpenOutfitActionListener(new OpenOutfitListener(outfit), "" + outfit.getId());
		}
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addHomeActionListener(new OpenHomeListener());
		view.addOpenOutfitsActionListener(new OpenOutfitsListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}
	
    class OpenOutfitListener implements ActionListener {
    	private Outfit outfit;
    	
    	public OpenOutfitListener(Outfit outfit) {
    		this.outfit = outfit;
    	}
    	
        public void actionPerformed(ActionEvent e) {
        	session.outfitPage(outfit);
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
    
    class OpenHomeListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		session.homePage();
    	}
    }
    
    class OpenOutfitsListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		session.outfitsPage();
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
