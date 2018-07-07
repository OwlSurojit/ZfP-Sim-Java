package geometry;

import java.io.Serializable;

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
}
