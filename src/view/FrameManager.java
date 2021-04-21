package view;
import javax.swing.*;
import java.awt.*;

public class FrameManager {

    JFrame frame;

    public FrameManager() {
        this.frame = new JFrame();
        frame = new JFrame("Outfit Rating MVC");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setNewPanel(JPanel panel){
        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public JFrame getFrame() {
        return frame;
    }
}
