
package shapesBase;

import drawing.Binding;
import enums.bindType;
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
    public ArrayList<Binding> getDragPoints(){
        ArrayList<Binding> list = new ArrayList<Binding>();
        list.add(new Binding(center, this, bindType.CIRCLE_CENTER));
        list.add(new Binding(new Point(center.x + radius, center.y), this, bindType.CIRCLE_PERIPHER));
        list.add(new Binding(new Point(center.x - radius, center.y), this, bindType.CIRCLE_PERIPHER));
        list.add(new Binding(new Point(center.x, center.y + radius), this, bindType.CIRCLE_PERIPHER));
        list.add(new Binding(new Point(center.x, center.y - radius), this, bindType.CIRCLE_PERIPHER));
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
}
