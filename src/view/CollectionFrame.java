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
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.domain.Collection;
import model.domain.Model;
import model.domain.Outfit;
import model.utilities.Observer;
import model.utilities.Subject;

public class CollectionFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Subject sub;
	private FrameManager fm;
	private JPanel mainPanel;
	private JPanel leftSide;
	private JPanel content;
	private JButton profilePageButton;
	private JButton homePageButton;
	private JButton outfitsPageButton;
	private JButton statisticsPageButton;
	private JButton logoutButton;
	private List<JButton> outfitButtons;
	
	public CollectionFrame(FrameManager fm) {
		this.fm = fm;
		this.outfitButtons = new ArrayList<>();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		
		setLeftSide();
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
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
		
		JLabel pageLabel = new JLabel("Collection", JLabel.CENTER);
		pageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		pageLabel.setFont(new Font(pageLabel.getFont().getName(), pageLabel.getFont().getStyle(), 20));
		
		JButton homePageButton = new JButton("Homepage");
		homePageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		homePageButton.setPreferredSize(new Dimension(100, 50));
		this.homePageButton = homePageButton;
		
		JButton outfitsPageButton = new JButton("Outfits");
		outfitsPageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		outfitsPageButton.setPreferredSize(new Dimension(100, 50));
		this.outfitsPageButton = outfitsPageButton;
		
		JButton profilePageButton = new JButton("My Profile");
		profilePageButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		profilePageButton.setPreferredSize(new Dimension(100, 50));
		this.profilePageButton = profilePageButton;
		
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
		leftSide.add(outfitsPageButton, gbc);
		leftSide.add(profilePageButton, gbc);
		leftSide.add(statisticsPageButton, gbc);
		leftSide.add(logoutButton, gbc);
		this.leftSide = leftSide;
	}
	
	public void setCards() {
		Collection collection = (Collection) sub;
		List<Outfit> outfits = collection.getOutfits();
		
        JPanel cards = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel collectionName = new JLabel("Collection Name: " + collection.getName());
		JLabel creatorName = new JLabel("Creator Name: " + collection.getCreator().getUsername());
		JLabel creationDate = new JLabel("Creation Date: " + collection.getCreationDate());
		
		cards.add(collectionName, gbc);
		cards.add(creatorName, gbc);
		cards.add(creationDate, gbc);
		
        for (Outfit outfit : outfits) {
        	JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	JButton outfitButton = new JButton();
        	JLabel brand = new JLabel(outfit.getBrand().toString());
        	JLabel color = new JLabel(outfit.getColor().toString());
        	JLabel type = new JLabel(outfit.getType().toString());

        	ImageIcon icon = null;
        	BufferedImage image = outfit.getImage();
        	if(image != null) {
        		Image iconImage = image.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        		icon = new ImageIcon(iconImage);
        	} else {
        		outfitButton.setText("This outfit does not have an image.");
        	}
        	
        	outfitButton.setName("" + outfit.getId());
        	outfitButton.setIcon(icon);
        	outfitButton.setBackground(java.awt.Color.WHITE);
        	outfitButtons.add(outfitButton);
        	
        	panel.setPreferredSize(new Dimension(350, 400));
        	outfitButton.setPreferredSize(new Dimension(350, 350));
        	
        	panel.add(outfitButton);
        	panel.add(brand);
        	panel.add(color);
        	panel.add(type);
        	
        	cards.add(panel, gbc);
        }
        content.add(new JScrollPane(cards));
        getFrameManager().setNewPanel(mainPanel);
    }
	
    public void addOpenOutfitActionListener(ActionListener actionListener, String outfitId) {
    	for (JButton jButton : outfitButtons) {
			if (jButton.getName().equals(outfitId)) {
				jButton.addActionListener(actionListener);
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
