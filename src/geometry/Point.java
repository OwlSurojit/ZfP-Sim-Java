package geometry;

import java.io.Serializable;
import drawing.DragPoint;
import java.util.ArrayList;

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
    
    public double dist(double x, double y){
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }
    
    public static Point center(ArrayList<Point> points){
        double x = 0;
        double y = 0;
        for(Point point : points){
            x += point.x;
            y += point.y;
        }
        x /= points.size();
        y /= points.size();
        return new Point(x, y);
    }
    
    public static Point center(Point p1, Point p2){
        double x = (p1.x + p2.x)/2;
        double y = (p1.y + p2.y)/2;
        return new Point(x, y);
    }
}
