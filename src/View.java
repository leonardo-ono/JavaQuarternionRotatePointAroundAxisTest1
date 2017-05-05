
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 *
 * @author leonardo
 */
public class View extends JPanel {
    
    private Font font = new Font("Arial", Font.BOLD, 20);
    private Sphere sphere = new Sphere();
    
    public View() {
        addKeyListener(new KeyHandler());
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw((Graphics2D) g);
    }
    
    private void draw(Graphics2D g) {
        Quaternion q = sphere.quaternionAccumulated;
        g.setFont(font);
        g.setColor(Color.BLACK);
        String text = "Accumulated quaternion Q(w, x, y, z) = " + String.format("(%5f, %.5f, %.5f, %.5f)", q.w, q.x, q.y, q.z).replace(",", ".");
        g.drawString(text, 20, 20);
        
        g.translate(getWidth() / 2, getHeight() / 2);
        g.scale(1, -1);
        
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(-getWidth() / 2, 0, getWidth() / 2, 0);
        g.drawLine(0, -getHeight() / 2, 0, getHeight() / 2);

        sphere.draw(g);
    }
    
    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getID() != KeyEvent.KEY_PRESSED) {
                return;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                sphere.rotate();
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                sphere.changeDirection(Math.toRadians(-5));
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                sphere.changeDirection(Math.toRadians(+5));
            }
            repaint();
        }
        
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                JFrame frame = new JFrame();
                frame.setTitle("Quaternion - Rotating points (vectors) around axis test using P' = Q.P.Q*");
                frame.getContentPane().add(view);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                
                view.requestFocus();
                view.requestFocusInWindow();
            }
        });
    }
    
}
