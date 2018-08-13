package shapesBase;

import geometry.Point;
import java.io.Serializable;
import java.util.ArrayList;
import structures.StructDrawingInfo;

public class Polygon extends ShapeBase implements Serializable {
    public Point[] points;
    public Line[] lines;
    
    public Polygon(Point[] points){
        this.points = points;
        this.lines = new Line[points.length-1];
        for(int i = 0; i<points.length -1; i++){
            lines[i] = new Line(points[i], points[i+1]);
        }
        
        this.drawingInfo = new StructDrawingInfo();
    }
    
    public boolean closed(){
        return points[0].equals(points[points.length]);
    }
    
    @Override
    public String toString(){
        String temp = "Polygon(";
        for(Line l : lines){
            temp += l + ", ";
        }
        return temp.substring(0, temp.length()-2) + ")";
    }

    @Override
    public ArrayList<ShapeBase> getComponents() {
        ArrayList<ShapeBase> list = new ArrayList<ShapeBase>();
        for(Line l : lines){
            list.add(l);
        }
        return list;
    }
    
    @Override
    public ArrayList<Point> getDragPoints(){
        ArrayList<Point> list = new ArrayList<Point>();
        for(Point p : points){
            if(! list.contains(p)){
                list.add(p);
            }
        }
        return list;
    }
}
