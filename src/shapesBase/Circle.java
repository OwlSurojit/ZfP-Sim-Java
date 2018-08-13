package shapesBase;

import geometry.Point;
import java.io.Serializable;
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import structures.StructDrawingInfo;

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
            
            this.drawingInfo = new StructDrawingInfo();
        }
    }
    
    @Override
    public String toString(){
        return "Circle(center = " + center + ",  radius = " + radius + ")";
    }
    
    @Override
    public ArrayList<ShapeBase> getComponents() {
        ArrayList<ShapeBase> list = new ArrayList<ShapeBase>();
        list.add(this);
        return list;
    }
    
    @Override
    public ArrayList<Point> getDragPoints(){
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(center);
        return list;
    }
}
