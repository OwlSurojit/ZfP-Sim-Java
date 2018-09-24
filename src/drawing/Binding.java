package drawing;

import enums.bindType;
import geometry.Point;
import shapesBase.ShapeBase;

public class Binding {
    public double x;
    public double y;
    public ShapeBase shape;
    public bindType type;
    
    public Binding(double x, double y, ShapeBase shape, bindType type){
        this.x = x;
        this.y = y;
        this.shape = shape;
        this.type = type;
    }
    
    public Binding(Point point, ShapeBase shape, bindType type){
        this.x = point.x;
        this.y = point.y;
        this.shape = shape;
        this.type = type;
    }
}
