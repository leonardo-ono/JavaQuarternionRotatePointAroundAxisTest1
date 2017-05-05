
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author leonardo
 */
public class Sphere {

    private final Vec3[][] points = new Vec3[45][35];
    private final Vec3 rotatedPoint = new Vec3();
    
    private double directionAngle = 0;
    private final Vec3 rotationAxis = new Vec3();
    private final Quaternion quaternion = new Quaternion();
    final Quaternion quaternionAccumulated;
            
    private final Stroke stroke = new BasicStroke(3);
    
    private Color[] colors = new Color[256];
    
    public Sphere() {
        changeDirection(Math.toRadians(0));
        quaternionAccumulated = new Quaternion(0, rotationAxis);
        
        createSpherePoints();
        createColors();
    }
    
    private void createColors() {
        for (int c=0; c<256; c++) {
            colors[c] = new Color(255 - c, 255 - c, 255 - c);
        }
    }
    
    private void createSpherePoints() {
        // create sphere points
        int count = 0;
        double scale = 100;
        for (double a = 85; a > -90; a -= 5) {
            double x = Math.cos(Math.toRadians(a));
            double y = Math.sin(Math.toRadians(a));
            for (int r = 0; r < 45; r++) {
                points[r][count] = new Vec3(x * scale, y * scale, 0);
            }
            count++;
        }
        for (int r = 0; r < 45; r++) {
            double angle = Math.toRadians(8 * r);
            for (int i = 0; i < 35; i++) {
                Vec3 p = points[r][i];
                p.rotateY(angle);
            }
        }
    }
    
    public void rotate() {
        quaternion.multiply(quaternionAccumulated, quaternionAccumulated);
    }

    public void changeDirection(double d) {
        directionAngle += d;
        double x = Math.cos(directionAngle);
        double y = Math.sin(directionAngle);
        rotationAxis.set(x, y, 0);
        quaternion.set(Math.toRadians(2), rotationAxis);
    }
        
    public void draw(Graphics2D g) {
        draw3DPoints(g);
        drawRotationAxis(g);
    }
    
    private void draw3DPoints(Graphics2D g) {
        for (int r = 0; r < 45; r++) {
            for (int i = 0; i < 35; i++) {
                quaternionAccumulated.rotate(points[r][i], rotatedPoint);
                Vec3 p = rotatedPoint;
                int ci = (int) p.z + 100;
                ci = ci < 0 ? 0 : ci > 255 ? 255 : ci;
                g.setColor(colors[ci]);
                
                // do perspective projection
                double sx = p.x / (300 - p.z) * 500;
                double sy = p.y / (300 - p.z) * 500;
                
                g.fillOval((int) (sx - 2), (int) (sy - 2), 4, 4);
            }
        }
    }
    
    private void drawRotationAxis(Graphics2D g) {
        Stroke originalStroke = g.getStroke();
        g.setStroke(stroke);
        g.setColor(Color.GREEN);
        double x1 = 220 * rotationAxis.x;
        double y1 = 220 * rotationAxis.y;
        double x2 = -220 * rotationAxis.x;
        double y2 = -220 * rotationAxis.y;
        g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g.setStroke(originalStroke);
    }

}
