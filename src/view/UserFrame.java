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
import javax.swing.BoxLayout;
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

public class UserFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Subject sub;
	private User currentUser;
	private User user;
	private FrameManager fm;
	private JPanel mainPanel;

	private JPanel leftSide;
	private JPanel content;
	private JButton createCollectionButton;
	private JButton unfollowButton;
	private JButton followButton;

	private JButton profilePageButton;
	private JButton homePageButton;
	private JButton outfitsPageButton;
	private JButton allUsersPageButton;
	private JButton statisticsPageButton;
	private JButton logoutButton;
	
	private List<JButton> collectionButtons;
	
	public UserFrame(FrameManager fm, User currentUser, User user) {
		this.fm = fm;
		this.currentUser = currentUser;
		this.collectionButtons = new ArrayList<>();
		this.user = user;
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
    	
		setLeftSide();
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		this.content = content;
		
		this.createCollectionButton = new JButton("Create New Collection");
		this.createCollectionButton.setPreferredSize(new Dimension(200, 50));
		this.createCollectionButton.setBackground(Color.PINK);

		this.unfollowButton =  new JButton("Unfollow");
		this.unfollowButton.setPreferredSize(new Dimension(100, 50));
		this.unfollowButton.setBackground(Color.RED);

		this.followButton =  new JButton("follow");
		this.followButton.setPreferredSize(new Dimension(100, 50));
		this.followButton.setBackground(Color.GREEN);

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
		
		JLabel pageLabel = new JLabel("User Profile", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));
		if (user.equals(currentUser)) {
			pageLabel.setText("My Profile");
		}
		
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

		JButton allUsersPageButton = new JButton("List All Users");
		allUsersPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		allUsersPageButton.setPreferredSize(new Dimension(100, 50));
		this.allUsersPageButton = allUsersPageButton;
		
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
		if (!user.equals(currentUser)) {
			leftSide.add(profilePageButton, gbc);
		}
		leftSide.add(outfitsPageButton, gbc);
		leftSide.add(allUsersPageButton, gbc);
		leftSide.add(statisticsPageButton, gbc);
		leftSide.add(logoutButton, gbc);
		this.leftSide = leftSide;
	}
	
	public void setCards() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel name = new JLabel("Username: " + user.getUsername());
        JLabel followers = new JLabel("Followers: " + user.getFollowers().size());
        JLabel followings = new JLabel("Followings: " + user.getFollowings().size());
        JLabel collectionsLabel = new JLabel("Collections:");
        
        List<Collection> collections = user.getCollections();
        
        JPanel collectionsPanel = new JPanel(new GridBagLayout());
        for (Collection collection : collections) {
			JButton collectionButton = new JButton(collection.getName());
			collectionButton.setPreferredSize(new Dimension(200, 50));
			collectionsPanel.add(collectionButton, gbc);
			collectionButtons.add(collectionButton);
		}
        
        panel.add(name, gbc);
        panel.add(followers, gbc);
        panel.add(followings, gbc);
        panel.add(followings, gbc);
        panel.add(collectionsLabel, gbc);
        if(user.equals(currentUser)) {
        	panel.add(createCollectionButton, gbc);
        }else{
        	if(currentUser.getFollowings().contains(user))
				panel.add(unfollowButton, gbc);
			else
				panel.add(followButton, gbc);

		}

		panel.add(collectionsPanel, gbc);
        
        content.removeAll();
        content.add(new JScrollPane(panel));
        getFrameManager().setNewPanel(mainPanel);
    }
	
	public void addOpenCollectionActionListener(ActionListener actionListener, String collectionName) {
    	for (JButton jButton : collectionButtons) {
			if (jButton.getText().equals(collectionName)) {
				jButton.addActionListener(actionListener);
			}
		}
    }
	
	public void addCreateCollectionActionListener(ActionListener actionListener) {
		createCollectionButton.addActionListener(actionListener);   
    }
	public void addUnfollowUserActionListener(ActionListener actionListener) {
		unfollowButton.addActionListener(actionListener);
	}

	public void addFollowUserActionListener(ActionListener actionListener) {
		followButton.addActionListener(actionListener);
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

	public void addAllUsersActionListener(ActionListener actionListener) {
		allUsersPageButton.addActionListener(actionListener);
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

    public String showInputDialog(String message) { 
    	return JOptionPane.showInputDialog(getFrameManager().getFrame(), message);
    }

	@Override
	public void update() {
		setCards();
	}


	@Override
	public void addSubject(Subject sub) {
		this.sub = sub;
	}


	@Override
	public void removeSubject(Subject sub) {
		this.sub = null;
	}
}
