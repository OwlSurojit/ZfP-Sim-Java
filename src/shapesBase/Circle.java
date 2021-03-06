
package shapesBase;

import drawing.Binding;
import enums.BindType;
import geometry.Point;
import geometry.Vector;
import geometry.Ray;
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
    
    public Ray getTangent(Point S){
        Vector r = (new Line(this.center, S)).toVector();
        return new Ray(S, r.toNormal()); //senkrecht zu r1
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
        list.add(new Binding(center, this, BindType.CIRCLE_CENTER));
        list.add(new Binding(new Point(center.x + radius, center.y), this, BindType.CIRCLE_PERIPHER));
        list.add(new Binding(new Point(center.x - radius, center.y), this, BindType.CIRCLE_PERIPHER));
        list.add(new Binding(new Point(center.x, center.y + radius), this, BindType.CIRCLE_PERIPHER));
        list.add(new Binding(new Point(center.x, center.y - radius), this, BindType.CIRCLE_PERIPHER));
        return list;
    }
    
    @Override
    public void refactor(Binding bind, double nx, double ny){
        switch(bind.type) {
            case CIRCLE_CENTER:
                center = new Point(nx, ny);
                break;
            case CIRCLE_PERIPHER:
                radius = center.dist(new Point(nx, ny));
                break;
            default:
                break;
        }
    }

    @Override
    public void rotate(double degree) {
        
    }
}
