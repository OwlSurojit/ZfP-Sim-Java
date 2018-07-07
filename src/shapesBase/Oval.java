package shapesBase;

import geometry.Point;
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class Oval extends ShapeBase{
    public Point p1;
    public Point p2;
    public double e;
    
    public Oval(Point p1, Point p2, double e){
        if(p1.equals(p2)){
            throw new ValueException("P1 and P2 mustn't be identical.");
        }
        else if(e <= (new Line(p1, p2)).length()){
            throw new ValueException("E must be greater than distance of P1 and P2.");
        }
        else{
            this.p1 = p1;
            this.p2 = p2;
            this.e = e;
        }
    }
    
    @Override
    public String toString(){
        return "Oval(p1 = " + p1 + ", p2 = " + p2 + ", e = " + e + ")";
    }
    
    @Override
    public ArrayList<ShapeBase> components() {
        ArrayList<ShapeBase> list = new ArrayList<ShapeBase>();
        list.add(this);
        return list;
    }
}
