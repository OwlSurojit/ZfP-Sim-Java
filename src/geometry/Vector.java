package geometry;

import java.lang.Math;

public class Vector {

    public double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector(Point start, Point end){
        this.x = end.x - start.x;
        this.y = end.y - start.y;
    }

    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector mul(double factor) {
        return new Vector(factor * this.x, factor * this.y);
    }

	public double dotP(Vector other) {
		return this.x * other.x + this.y * other.y;
	}

    public Vector toNormal(){
        return new Vector(-this.y, this.x);
    }

	@Override
    public String toString() {
        return String.format("(%g %g)", this.x, this.y);
    }

    public double length() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public boolean equals(Vector other) {
        return (this.x * other.y == this.y * other.x);
    }

    public Point toPoint() {
        return new Point(this.x, this.y);
    }

    public double getDirAngle(Vector other) { // Winkel im math. pos. Drehsinn zu anderem Vektor bei gleichem Start
        return (-Math.toDegrees(Math.atan2(this.x * other.y - this.y * other.x, this.x * other.x + this.y * other.y)) + 360) % 360;
    }

	public double getAngle(Vector other) {
		return Math.toDegrees(Math.acos(this.dotP(other) / (this.length() * other.length())));
	}

	public shapesBase.Line toLine() {
		return new shapesBase.Line(new Point(0, 0), this.toPoint());
	}
}
