package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import controller.HomeController.LogoutListener;
import controller.HomeController.OpenOutfitsListener;
import controller.HomeController.OpenStatisticsListener;
import controller.HomeController.OpenUserListener;
import model.domain.Collection;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
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
		
		List<Outfit> outfits = model.getAllOutfits();
		view.setCards(outfits);
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
