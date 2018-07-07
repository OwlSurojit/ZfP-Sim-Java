package shapesBase;

import geometry.Point;
import java.io.Serializable;
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class Circle extends ShapeBase implements Serializable {
    public Point center;
    public double radius;
    
    public Circle(Point center, double radius){
        if(radius <= 0){
            throw new ValueException("Radius of Circle must be greater than zero.");
        }
        else{
            this.center = center;
            this.radius = radius;
        }
    }
    
    @Override
    public String toString(){
        return "Circle(center = " + center + ",  radius = " + radius + ")";
    }
    
    @Override
    public ArrayList<ShapeBase> components() {
        ArrayList<ShapeBase> list = new ArrayList<ShapeBase>();
        list.add(this);
        return list;
    }
}
