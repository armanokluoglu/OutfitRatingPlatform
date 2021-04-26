package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import model.domain.Collection;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
import view.CollectionFrame;

public class CollectionController {

	private SessionManager session;
	private Model model; 
	private CollectionFrame view;
	private Collection collection;
	private List<Outfit> outfits;

	public CollectionController(Model model, CollectionFrame view, SessionManager session, Collection collection) {
		this.session = session;
		this.model = model;
		this.view = view;
		this.collection = collection;
		
		view.addSubject(collection);
		collection.register(view);
		
		this.outfits = collection.getOutfits();
		view.setCards();
		setSidebarListeners();
		setContentListeners();
	}
	
	private void setSidebarListeners() {
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addHomeActionListener(new OpenHomeListener());
		view.addOpenOutfitsActionListener(new OpenOutfitsListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}
	
	private void setContentListeners() {
		for (Outfit outfit : outfits) {
			view.addOpenOutfitActionListener(new OpenOutfitListener(outfit), "" + outfit.getId());
		}
		view.addAddOutfitActionListener(new AddOutfitListener());
	}
	
	class AddOutfitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	List<Outfit> allOutfits = model.getAllOutfits();
        	String[] choices = new String[allOutfits.size()];
        	for (int i = 0; i < allOutfits.size(); i++) {
        		Outfit outfit = allOutfits.get(i);
				choices[i] = outfit.getId() + ". " + outfit.getBrand() + " " + outfit.getColor() + " " + outfit.getType();
			}
        	Object response = (view.showInputMessage("Choose an outfit to add:", choices));
        	if (response == null || response == "") {
        		return;
        	}
        	String str = response.toString();
        	String outfitId = str.substring(0, str.indexOf("."));
        	try {
        		model.addOutfitToCollection(Integer.parseInt(outfitId), collection);
        	} catch (IllegalStateException e1) {
        		view.showMessage(e1.getMessage());
        		return;
        	}
        	for (Outfit outfit : outfits) {
    			view.addOpenOutfitActionListener(new OpenOutfitListener(outfit), "" + outfit.getId());
    		}
        }
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
