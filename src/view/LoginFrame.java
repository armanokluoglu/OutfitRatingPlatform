package view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = -7798292689167507569L;
	private JTextField usernameField = new JTextField(20);
	private JPasswordField passwordField = new JPasswordField(20);;
	private JButton loginButton = new JButton("Login");
	private FrameManager fm;
	
	public LoginFrame(FrameManager fm) {
		this.fm = fm;
		JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Username: "));
        content.add(usernameField);
        content.add(new JLabel("Password: "));
        content.add(passwordField);
        content.add(loginButton);
        getFrameManager().setNewPanel(content);
	}
	
    public void addLoginActionListener(ActionListener actionListener) {
        loginButton.addActionListener(actionListener);
    }

    public String getUsername() {
        return this.usernameField.getText();
    }

    public char[] getPassword() {
        return this.passwordField.getPassword();
    }

    public FrameManager getFrameManager() {
        return this.fm;
    }

    public void showMessage(String message) { 
    	JOptionPane.showMessageDialog(getFrameManager().getFrame(), message);
    }
}
