package shapesBase;

import shapesBase.Line;
import geometry.Point;
import geometry.Vector;
import java.io.Serializable;
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import structures.StructDrawingInfo;

public class CircleArc extends ShapeBase implements Serializable {
    public Point center;
    public double radius;
    public double offsetangle;
    public double arcangle;
    
    // beginnend in x-Richtung, math. positiv
    public CircleArc(Point center, double radius, double offsetangle, double arcangle){
        if(arcangle <= 0){
            throw new ValueException("Angle of arc must be greater than zero");
        }
        else{
            this.center = center;
            this.radius = radius;
            this.offsetangle = offsetangle;
            this.arcangle = arcangle;
            
            this.drawingInfo = new StructDrawingInfo();
        }
    }
    
    public CircleArc(Point center, Point start, Point end){
        this.center = center;
        this.radius = (new Line(center, start)).length();
        this.offsetangle = (new Vector(1, 0)).getDirAngle((new Line(center, start)).toVector());
        this.arcangle = ((new Line(center, start)).toVector()).getDirAngle((new Line(center, end)).toVector());
        
    }
    
    /*public double getRadius(){
        return (new Line(center, start)).length();
    }*/
    
    @Override
    public String toString(){
        return "Arc(center = " + center + ", radius = " + radius + ", arcangle = " + arcangle + " offsetangle = " + offsetangle + ")";
    }
    
    public Circle toCircle(){
        return new Circle(this.center, this.radius);
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
