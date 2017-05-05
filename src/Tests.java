/**
 *
 * @author leonardo
 */
public class Tests {

    public static void main(String[] args) {
        Vec3 point = new Vec3(1, 0, 0);
        Vec3 rotatedPoint = new Vec3();

        Quaternion qr = new Quaternion(Math.toRadians(0), new Vec3(0, 0, 1));
        Quaternion r1 = new Quaternion(Math.toRadians(45), new Vec3(0, 0, 1));
        
        r1.multiply(qr, qr);
        
        qr.rotate(point, rotatedPoint);
        System.out.println(rotatedPoint + " correct value: " + Math.sqrt(2) / 2);
    }
        
    // accumulating rotations
    private static void test2() {
        Vec3 point = new Vec3(1, 0, 0);
        Vec3 rotatedPoint = new Vec3();
        
        Quaternion q1 = new Quaternion(Math.toRadians(1), new Vec3(0, 0, 1));
        Quaternion qr = new Quaternion(Math.toRadians(0), new Vec3(0, 0, 1));
        
        for (int i=0; i<90; i++) {
            q1.multiply(qr, qr);
            qr.rotate(point, rotatedPoint);   
            System.out.println("angle=" + (i + 1) + " = " + rotatedPoint);
        }
    }
    
    // rotating a point (vector) around axis
    private static void test() {
        
        Vec3 point = new Vec3(2, 0, 0);
        Vec3 rotatedPoint = new Vec3();
        
        Quaternion q = new Quaternion(Math.toRadians(45), new Vec3(1, 0, 1));
        q.rotate(point, rotatedPoint);
        
        System.out.println(rotatedPoint + " " + Math.sqrt(2) / 2);
    }
    
}
