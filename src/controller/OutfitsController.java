package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
import view.OutfitsFrame;

public class OutfitsController {

	private SessionManager session;

	public OutfitsController(Model model, OutfitsFrame view, SessionManager session) {
		this.session = session;
		
		view.addSubject(model);
		model.register(view);
		
		List<Outfit> outfits = model.getAllOutfits();
		view.setCards();
		
		for (Outfit outfit : outfits) {
			view.addOpenOutfitActionListener(new OpenOutfitListener(outfit), "" + outfit.getId());
		}
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addHomeActionListener(new OpenHomeListener());
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
