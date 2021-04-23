package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.domain.Collection;
import model.domain.Model;
import model.domain.User;
import model.utilities.Observer;
import model.utilities.Subject;

public class StatisticsFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Model model;
	private FrameManager fm;
	private JPanel mainPanel;
	private JPanel leftSide;
	private JPanel content;
	private JButton homePageButton;
	private JButton profilePageButton;
	private JButton outfitsPageButton;
	private JButton logoutButton;
	
	public StatisticsFrame(Model model, FrameManager fm) {
		this.fm = fm;
		this.model = model;
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		
		setLeftSide();
		JPanel content = new JPanel();
		this.content = content;
		
		mainPanel.add(leftSide);
		mainPanel.add(content);
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
		
		JLabel pageLabel = new JLabel("Statistics", JLabel.CENTER);
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
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		logoutButton.setPreferredSize(new Dimension(100, 50));
		this.logoutButton = logoutButton;
		
		leftSide.add(titleLabel, gbc);
		leftSide.add(pageLabel, gbc);
		leftSide.add(homePageButton, gbc);
		leftSide.add(profilePageButton, gbc);
		leftSide.add(outfitsPageButton, gbc);
		leftSide.add(logoutButton, gbc);
		this.leftSide = leftSide;
	}
	
	public void setContent(String mostLikedOutfit, String mostDislikedOutfit, String mostFollowedUser) {
		content.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
		
		JLabel mostLikedLabel = new JLabel("Most liked outfit: " + mostLikedOutfit);
		mostLikedLabel.setFont(new Font(mostLikedLabel.getFont().getName(), mostLikedLabel.getFont().getStyle(), 20));
		JLabel mostDislikedLabel = new JLabel("Most disliked outfit: " + mostDislikedOutfit);
		mostDislikedLabel.setFont(new Font(mostDislikedLabel.getFont().getName(), mostDislikedLabel.getFont().getStyle(), 20));
		JLabel mostFollowedLabel = new JLabel("Most followed user: " + mostFollowedUser);
		mostFollowedLabel.setFont(new Font(mostFollowedLabel.getFont().getName(), mostFollowedLabel.getFont().getStyle(), 20));
		
		content.add(mostLikedLabel, gbc);
		content.add(mostDislikedLabel, gbc);
		content.add(mostFollowedLabel, gbc);
		mainPanel.add(content);
		getFrameManager().setNewPanel(mainPanel);
	}
	
    public void addOpenUserActionListener(ActionListener actionListener) {
    	profilePageButton.addActionListener(actionListener);   
    }
    
    public void addOutfitsActionListener(ActionListener actionListener) {
    	outfitsPageButton.addActionListener(actionListener);
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


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addSubject(Subject sub) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeSubject(Subject sub) {
		// TODO Auto-generated method stub
		
	}
}