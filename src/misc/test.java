package misc;
import geometry.Vector;

public class test {
	public static void main(String[] args) {
		Vector v1 = new Vector(1, 0);
		Vector v2 = new Vector(1, 1);
		Vector v3 = new Vector(-1, 1);
		Vector v4 = new Vector(-1, -1);
		System.out.println(v1.getAngle(v2));
		System.out.println(v1.getAngle(v3));
		System.out.println(v1.getAngle(v4));
		System.out.println(v2.getAngle(v4));
		System.out.println(v1.getDirAngle(v2));
		System.out.println(v1.getDirAngle(v3));
		System.out.println(v1.getDirAngle(v4));
                
                Double a = Math.sqrt(-1);
                if (a.isNaN()) a = null;
		System.out.println(Math.pow(5*a, 2));
                
	}
}
