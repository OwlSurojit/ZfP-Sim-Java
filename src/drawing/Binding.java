package drawing;

import enums.BindType;
import geometry.Point;
import java.io.Serializable;
import shapesBase.ShapeBase;

public class Binding implements Serializable{
    public double x;
    public double y;
    public ShapeBase shape;
    public BindType type;
    
    public Binding(double x, double y, ShapeBase shape, BindType type){
        this.x = x;
        this.y = y;
        this.shape = shape;
        this.type = type;
    }
    
    public Binding(Point point, ShapeBase shape, BindType type){
        this.x = point.x;
        this.y = point.y;
        this.shape = shape;
        this.type = type;
    }
}
