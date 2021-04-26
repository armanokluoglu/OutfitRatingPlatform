package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import model.domain.Comment;
import model.domain.Model;
import model.domain.Outfit;
import model.domain.User;
import view.OutfitFrame;

public class OutfitController {

	private SessionManager session;
	private Model model;
	private Outfit outfit;
	private OutfitFrame view;

	public OutfitController(Model model, OutfitFrame view, SessionManager session, Outfit outfit) {
		this.model = model;
		this.view = view;
		this.session = session;
		this.outfit = outfit;
		
		view.addSubject(outfit);
		outfit.register(view);
		
		view.setCurrentUserId(session.getCurrentUser().getId());
		view.setOutfit();
		
		view.addLikeOutfitActionListener(new LikeOutfitListener());
		view.addDislikeOutfitActionListener(new DislikeOutfitListener());
		view.addCommentOnOutfitActionListener(new AddCommentOnOutfitListener());
		List<Comment> comments = outfit.getComments();
		for (Comment comment : comments) {
			view.addRemoveCommentOnOutfitActionListener(new RemoveCommentOnOutfitListener(comment), comment.getId());
		}
		
		view.addOpenProfileActionListener(new OpenUserListener(session.getCurrentUser()));
		view.addLogoutActionListener(new LogoutListener());
		view.addOpenOutfitsActionListener(new OpenOutfitsListener());
		view.addHomeActionListener(new OpenHomeListener());
		view.addStatisticsActionListener(new OpenStatisticsListener());
	}
	
	class LikeOutfitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	model.likeOutfitAsUser(outfit, session.getCurrentUser());
        }
    }
	
	class DislikeOutfitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	model.dislikeOutfitAsUser(outfit, session.getCurrentUser());
        }
    }
	
	class AddCommentOnOutfitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	Comment comment = model.commentOnOutfitAsUser(outfit, view.getComment(), session.getCurrentUser());
        	view.addRemoveCommentOnOutfitActionListener(new RemoveCommentOnOutfitListener(comment), comment.getId());
        	view.clearComment();
        }
    }
	
    class RemoveCommentOnOutfitListener implements ActionListener {
    	private Comment comment;
    	
    	public RemoveCommentOnOutfitListener(Comment comment) {
    		this.comment = comment;
    	}
    	
        public void actionPerformed(ActionEvent e) {
        	model.removeCommentOnOutfit(outfit, comment);
        	List<Comment> comments = outfit.getComments();
    		for (Comment comment : comments) {
    			view.addRemoveCommentOnOutfitActionListener(new RemoveCommentOnOutfitListener(comment), comment.getId());
    		}
        }
    }
	
    class OpenOutfitsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	session.outfitsPage();
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
