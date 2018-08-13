package geometry;

import java.io.Serializable;
import drawing.DragPoint;

public class Point implements Serializable {
    public double x;
    public double y;
    
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public boolean equals(Point other){
        return misc.Tools.equal(x, other.x) && misc.Tools.equal(y, other.y);
    }
    
    @Override
    public String toString(){
        return "("+x+"|"+y+")";
    }
    
    public Vector toVector(){
        return new Vector(x, y);
    }
    
    public double dist(Point p2){
        return Math.sqrt(Math.pow(p2.x - this.x, 2) + Math.pow(p2.y - this.y, 2));
    }
    
    public double dist(DragPoint p2){
        return Math.sqrt(Math.pow(p2.x - this.x, 2) + Math.pow(p2.y - this.y, 2));
    }
}
