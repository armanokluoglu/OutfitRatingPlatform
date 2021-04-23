package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
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

public class OutfitsFrame extends JFrame implements Observer {

	private static final long serialVersionUID = -4853864434524144396L;
	private Model model;
	private FrameManager fm;
	private JPanel content;
	private List<JButton> userButtons;
	private List<JButton> collectionButtons;
	
	public OutfitsFrame(Model model, FrameManager fm) {
		this.fm = fm;
		this.model = model;
		this.userButtons = new ArrayList<>();
		this.collectionButtons = new ArrayList<>();
		JPanel content = new JPanel();
        content.setLayout(new CardLayout());
        this.content = content;
        getFrameManager().setNewPanel(content);
	}
	
	public void setCards(List<Collection> collections) {
        JPanel cards = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        for (Collection collection : collections) {
        	User user = collection.getCreator();
        	JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	
        	String userName = user.getUsername();
        	String collectionName = collection.getName();
        	
        	JButton userButton = new JButton(userName);
        	JButton collectionButton = new JButton(collectionName);
        	userButtons.add(userButton);
        	collectionButtons.add(collectionButton);
        	
        	panel.setPreferredSize(new Dimension(300, 60));
        	userButton.setPreferredSize(new Dimension(100, 50));
        	collectionButton.setPreferredSize(new Dimension(100, 50));
        	
        	panel.add(userButton);
        	panel.add(collectionButton);
        	
        	cards.add(panel, gbc);
        }
        content.add(new JScrollPane(cards));
        getFrameManager().setNewPanel(content);
    }
	
    public void addOpenCollectionActionListener(ActionListener actionListener, String collectionName) {
    	for (JButton jButton : collectionButtons) {
			if (jButton.getText().equals(collectionName)) {
				jButton.addActionListener(actionListener);
			}
		}
    }
    
    public void addOpenUserActionListener(ActionListener actionListener, String userName) {
    	for (JButton jButton : userButtons) {
			if (jButton.getText().equals(userName)) {
				jButton.addActionListener(actionListener);
			}
		}    
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
