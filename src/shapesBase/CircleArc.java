package shapesBase;

import shapesBase.Line;
import geometry.Point;
import geometry.Point;
import java.io.Serializable;
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

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
        }
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
    public ArrayList<ShapeBase> components() {
        ArrayList<ShapeBase> list = new ArrayList<ShapeBase>();
        list.add(this);
        return list;
    }
}
