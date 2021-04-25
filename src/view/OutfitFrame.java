package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import model.domain.Comment;
import model.domain.Model;
import model.domain.Outfit;
import model.utilities.Observer;
import model.utilities.Subject;

public class OutfitFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Subject sub;
	private FrameManager fm;
	private int currentUserId;
	private JPanel mainPanel;
	private JPanel leftSide;
	private JPanel content;
	
	private JButton likeButton;
	private JButton dislikeButton;
	private JTextField commentField;
	private List<CommentPanel> commentPanels;
	private List<JButton> removeCommentButtons;
	
	private JButton profilePageButton;
	private JButton homePageButton;
	private JButton outfitsPageButton;
	private JButton statisticsPageButton;
	private JButton logoutButton;
	
	public OutfitFrame(FrameManager fm) {
		this.fm = fm;
		this.currentUserId = 0;
		this.commentPanels = new ArrayList<>();
		this.removeCommentButtons = new ArrayList<>();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		
		JButton likeButton = new JButton("👍");
    	this.likeButton = likeButton;
    	
    	JButton dislikeButton = new JButton("👎");
    	this.dislikeButton = dislikeButton;
    	
    	JTextField commentField = new JTextField();
    	commentField.setPreferredSize(new Dimension(300, 30));
    	this.commentField = commentField;
    	
		setLeftSide();
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		this.content = content;
		
		mainPanel.add(leftSide);
		mainPanel.add(this.content);
		this.mainPanel = mainPanel;
        getFrameManager().setNewPanel(mainPanel);
	}
	
	public void setLeftSide() {
		JPanel leftSide = new JPanel();
		leftSide.setLayout(new GridBagLayout());
		leftSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
		
		JLabel titleLabel = new JLabel("Outfit Rating MVC", JLabel.CENTER);
		titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));
		
		JLabel pageLabel = new JLabel("Outfit", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));
		
		JButton homePageButton = new JButton("Homepage");
		homePageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		homePageButton.setPreferredSize(new Dimension(100, 50));
		this.homePageButton = homePageButton;
		
		JButton profilePageButton = new JButton("My Profile");
		profilePageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		profilePageButton.setPreferredSize(new Dimension(100, 50));
		this.profilePageButton = profilePageButton;
		
		JButton outfitsPageButton = new JButton("Outfits");
		outfitsPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		outfitsPageButton.setPreferredSize(new Dimension(100, 50));
		this.outfitsPageButton = outfitsPageButton;
		
		JButton statisticsPageButton = new JButton("Statistics");
		statisticsPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		statisticsPageButton.setPreferredSize(new Dimension(100, 50));
		this.statisticsPageButton = statisticsPageButton;
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		logoutButton.setPreferredSize(new Dimension(100, 50));
		this.logoutButton = logoutButton;
		
		leftSide.add(titleLabel, gbc);
		leftSide.add(pageLabel, gbc);
		leftSide.add(homePageButton, gbc);
		leftSide.add(profilePageButton, gbc);
		leftSide.add(outfitsPageButton, gbc);
		leftSide.add(statisticsPageButton, gbc);
		leftSide.add(logoutButton, gbc);
		this.leftSide = leftSide;
	}
	
	public void setOutfit() {
		Outfit outfit = (Outfit) sub;
		JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
    	
    	JLabel outfitImage = new JLabel();
    	JLabel brand = new JLabel("Brand: " + outfit.getBrand().toString());
    	JLabel color = new JLabel("Color: " + outfit.getColor().toString());
    	JLabel type = new JLabel("Type: " + outfit.getType().toString());
    	JLabel sizes = new JLabel("Sizes: " + outfit.getSizes().toString());
    	JLabel occasion = new JLabel("Occasion: " + outfit.getOccasion().toString());
    	JLabel gender = new JLabel("Gender: " + outfit.getGender().toString());
    	JLabel likes = new JLabel("" + outfit.getLikes());
    	JLabel dislikes = new JLabel("" + outfit.getDislikes());
    	JLabel leaveComment = new JLabel("Leave a comment: ");
    	
    	ImageIcon icon = null;
    	BufferedImage image = outfit.getImage();
    	if(image != null) {
    		Image iconImage = image.getScaledInstance(320, 320, Image.SCALE_SMOOTH);
    		icon = new ImageIcon(iconImage);
    	} else {
    		outfitImage.setText("This outfit does not have an image.");
    		Border border = BorderFactory.createLineBorder(Color.BLACK);
        	outfitImage.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(150,60,150,60)));
    	}
    	
    	outfitImage.setName("" + outfit.getId());
    	outfitImage.setIcon(icon);
    	outfitImage.setBackground(java.awt.Color.WHITE);
    	outfitImage.setPreferredSize(new Dimension(320, 320));
    	
    	panel.add(outfitImage, gbc);
    	panel.add(brand, gbc);
    	panel.add(type, gbc);
    	panel.add(color, gbc);
    	panel.add(gender, gbc);
    	panel.add(occasion, gbc);
    	panel.add(sizes, gbc);
    	
    	JPanel likePanel = new JPanel();
    	likePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	likePanel.add(likes);
    	likePanel.add(likeButton);
    	likePanel.add(dislikeButton);
    	likePanel.add(dislikes);
    	panel.add(likePanel, gbc);
    	
    	panel.add(leaveComment, gbc);
    	panel.add(commentField, gbc);
    	panel.add(getCommentsPanel(outfit.getComments()), gbc);
    	
    	content.removeAll();
		content.add(new JScrollPane(panel));
        getFrameManager().setNewPanel(mainPanel);
    }
	
	@Override
	public void update() {
		setOutfit();
	}

	@Override
	public void addSubject(Subject sub) {
		this.sub = sub;
	}

	@Override
	public void removeSubject(Subject sub) {
		this.sub = null;
	}
	
	public void setCurrentUserId(int currentUserId) {
		this.currentUserId = currentUserId;
	}
	
	private JPanel getCommentsPanel(List<Comment> comments) {
		JPanel cards = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(20, 10, 10, 10);
        
        for (Comment comment : comments) {
        	CommentPanel commentPanel = new CommentPanel(comment);
        	cards.add(commentPanel.panel, gbc);
        	commentPanels.add(commentPanel);
		}
        return cards;
	}
	
	public void addLikeOutfitActionListener(ActionListener actionListener) {
		likeButton.addActionListener(actionListener); 
	}
	
	public void addDislikeOutfitActionListener(ActionListener actionListener) {
		dislikeButton.addActionListener(actionListener); 
	}
	
	public void addCommentOnOutfitActionListener(ActionListener actionListener) {
		commentField.addActionListener(actionListener); 
	}
	
	public void addRemoveCommentOnOutfitActionListener(ActionListener actionListener, int commentId) {
		for (CommentPanel commentPanel : commentPanels) {
			if (commentId == commentPanel.comment.getId()) {
				commentPanel.removeButton.addActionListener(actionListener);
			}
		}
	}
	
    public void addOpenProfileActionListener(ActionListener actionListener) {
    	profilePageButton.addActionListener(actionListener);   
    }
    
    public void addOpenOutfitsActionListener(ActionListener actionListener) {
    	outfitsPageButton.addActionListener(actionListener);   
    }
    
    public void addStatisticsActionListener(ActionListener actionListener) {
    	statisticsPageButton.addActionListener(actionListener);
    }
    
    public void addHomeActionListener(ActionListener actionListener) {
    	homePageButton.addActionListener(actionListener);
    }
    
    public void addLogoutActionListener(ActionListener actionListener) {
    	logoutButton.addActionListener(actionListener);
    }

    public FrameManager getFrameManager() {
        return this.fm;
    }

    public void showMessage(String message) { 
    	JOptionPane.showMessageDialog(getFrameManager().getFrame(), message);
    }

    public String getComment() {
    	return commentField.getText();
    }
    
    public void clearComment() {
    	commentField.setText("");
    }
    
	private class CommentPanel {
		private JPanel panel;
		private JLabel author;
		private JLabel date;
		private JLabel content;
		private JButton removeButton;
		private Comment comment;
		
		public CommentPanel(Comment comment) {
			this.comment = comment;
			this.panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			this.author = new JLabel(comment.getAuthor().getUsername());
			this.date = new JLabel((new Date()).toString());
			//this.date = new JLabel(comment.getDate().toString());
			this.content = new JLabel(comment.getContent());
			this.removeButton = new JButton("🗑");
			
			panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
			JPanel header = new JPanel();
			header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
			header.add(author);
			header.add(Box.createHorizontalGlue());
			header.add(date);
			
			JPanel main = new JPanel();
			main.setLayout(new BoxLayout(main, BoxLayout.LINE_AXIS));
			main.add(content);
			main.add(Box.createHorizontalGlue());
			if (currentUserId == comment.getAuthor().getId()) {
				main.add(removeButton);
				removeCommentButtons.add(removeButton);
			}
		    
			panel.setPreferredSize(new Dimension(300, 50));
			
			panel.add(header);
			panel.add(main);
		}
	}
}
