package shapesBase;

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

    public Line reverse() {
            return new Line(this.end, this.start);
    }

    public Vector toVector(){
        return new Vector(end.x-start.x, end.y-start.y);
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
    public ArrayList<Point> getDragPoints(){
        ArrayList<Point> list = new ArrayList<Point>();
        list.add(start);
        list.add(end);
        return list;
    }
}
