package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
import model.utilities.Observer;
import model.utilities.Subject;
import view.OutfitsFrame;

public class OutfitsController implements Observer {

	private SessionManager session;
	private OutfitsFrame view;
	private List<Outfit> outfits;
	private Subject model;

	public OutfitsController(Model model, OutfitsFrame view, SessionManager session) {
		this.session = session;
		this.view = view;
		model.register(this);
		this.outfits = model.getAllOutfits();

		setSidebarListeners();
		setContentListeners();
	}
	
	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addHomeActionListener(new OpenHomeListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}
	
	private void setContentListeners() {
		for (Outfit outfit : outfits) {
			view.addOpenOutfitActionListener(new OpenOutfitListener(outfit), "" + outfit.getId());
		}
	}
	@Override
	public void update() {
		setContentListeners();
	}

	@Override
	public void addSubject(Subject sub) {
		this.model = sub;
	}

	@Override
	public void removeSubject(Subject sub) {
		this.model = null;

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
