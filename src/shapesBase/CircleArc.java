package shapesBase;

import drawing.Binding;
import enums.BindType;
import geometry.*;
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
    
    @Override
    public String toString(){
        return "Arc(center = " + center + ", radius = " + radius + ", arcangle = " + arcangle + " offsetangle = " + offsetangle + ")";
    }
    
    public Circle toCircle(){
        return new Circle(this.center, this.radius);
    }
    
    public Point P1(){
        return this.center.toVector().add((new Vector(1, 0)).rotate(this.offsetangle).mul(this.radius)).toPoint();
    }
    
    public Point P2(){
        return this.center.toVector().add((new Vector(1, 0)).rotate(this.offsetangle + this.arcangle).mul(this.radius)).toPoint();
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
        list.add(new Binding(center, this, BindType.CIRCLEARC_CENTER));
        list.add(new Binding((new Ray(center, (new Vector(1, 0)).rotate(offsetangle))).getPoint(radius), this, BindType.CIRCLEARC_START));
        list.add(new Binding((new Ray(center, (new Vector(1, 0)).rotate(offsetangle + arcangle))).getPoint(radius), this, BindType.CIRCLEARC_END));
        list.add(new Binding((new Ray(center, (new Vector(1, 0)).rotate(offsetangle + arcangle/2))).getPoint(radius), this, BindType.CIRCLEARC_RADIUS));
        return list;
    }
    
    @Override
    public void refactor(Binding bind, double nx, double ny){
        switch(bind.type) {
            case CIRCLEARC_CENTER:
                center = new Point(nx, ny);
                break;
            case CIRCLEARC_START:
                offsetangle = (new Vector(1, 0)).getDirAngle((new Line(center, new Point(nx, ny))).toVector());
                break;
            case CIRCLEARC_END:
                arcangle = (new Vector(1, 0)).rotate(offsetangle).getDirAngle((new Line(center, new Point(nx, ny))).toVector());
                break;
            case CIRCLEARC_RADIUS:
                radius = center.dist(new Point(nx, ny));
                break;
            default:
                break;
        }
    }

    @Override
    public void rotate(double degree) {
        offsetangle = (offsetangle-degree) % 360;
    }
}
