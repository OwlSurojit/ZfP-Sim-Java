package shapesBase;

import drawing.Binding;
import enums.bindType;
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
        
        this.drawingInfo = new StructDrawingInfo();
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
    public ArrayList<Binding> getDragPoints(){
        ArrayList<Binding> list = new ArrayList<Binding>();
        list.add(new Binding(center, this, bindType.CARC_CENTER));
        return list;
    }
    
    @Override
    public void refactor(Binding bind, double nx, double ny){
        center = new Point(nx, ny);
    }

    @Override
    public void rotate(double degree) {
        offsetangle = (offsetangle-degree) % 360;
    }
}
