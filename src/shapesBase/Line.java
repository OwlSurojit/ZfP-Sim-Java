package shapesBase;

import drawing.Binding;
import enums.BindType;
import geometry.Point;
import geometry.Ray;
import geometry.Vector;
import java.io.Serializable;
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import structures.StructDrawingInfo;

public class Line extends ShapeBase implements Serializable {
    public Point start;
    public Point end;
    
    public Line(Point start, Point end){
        if(start.equals(end)){
            throw new ValueException("Start and end points of a line mustn't be identical.");
        }
        else{
            this.start = start;
            this.end = end;
            
            this.drawingInfo = new StructDrawingInfo();
        }
    }

    @Override
    public String toString(){
        return "Line(" + start + ", " + end + ")";
    }

    public double length(){
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
    }

    public Line reverse(){
        return new Line(this.end, this.start);
    }
    
    public Point getCenter(){
        return Point.center(start, end);
    }

    public Vector toVector(){
        return new Vector(start, end);
    }

    public Ray toRay(){
        return new Ray(start, toVector());
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
        list.add(new Binding(start, this, BindType.LINE_START));
        list.add(new Binding(getCenter(), this, BindType.LINE_CENTER));
        list.add(new Binding(end, this, BindType.LINE_END));
        return list;
    }
    
    @Override
    public void refactor(Binding bind, double nx, double ny){
        switch(bind.type){
            case LINE_CENTER:
                Point c = getCenter();
                double tx = (nx-c.x);
                double ty = (ny-c.y);
                start = new Point(start.x + tx, start.y + ty);
                end = new Point(end.x + tx, end.y + ty);                
                break;
            case LINE_START:
                start = new Point(nx, ny);
                break;
            case LINE_END:
                end = new Point(nx, ny);
                break;
            default:
                break;
        }
    }

    @Override
    public void rotate(double degree) {
        Point c = getCenter();
        start.rotateAround(c, degree);
        end.rotateAround(c, degree);
    }
}
