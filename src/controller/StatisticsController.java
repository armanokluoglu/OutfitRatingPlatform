package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.domain.Collection;
import model.domain.Model;
import model.domain.User;
import view.HomeFrame;
import view.OutfitFrame;
import view.StatisticsFrame;

public class StatisticsController {

	private SessionManager session;
	private Model model;
	private StatisticsFrame view;

	public StatisticsController(Model model, StatisticsFrame view, SessionManager session) {
		this.model = model;
		this.view = view;
		this.session = session;
		
		view.setContent("1", "2", "3");
		
		view.addSubject(model);
		view.addHomeActionListener(new OpenHomeListener());
		view.addLogoutActionListener(new LogoutListener());
		view.addOutfitsActionListener(new OpenOutfitsListener());
		view.addOpenUserActionListener(new OpenUserListener(session.getCurrentUser()));
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
    
    class OpenStatisticsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.statisticsPage();
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
    
    class LogoutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.loginPage();
        }
    }
}
