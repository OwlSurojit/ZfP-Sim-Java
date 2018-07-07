package misc;
import geometry.Vector;

public class test {
	public static void main(String[] args) {
		Vector v1 = new Vector(1, 0);
		Vector v2 = new Vector(1, 1);
		System.out.println(v1.getAngle(v2));
	}
}
