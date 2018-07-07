package shapesBase;

import shapesBase.Line;
import geometry.Point;
import geometry.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Polygon extends ShapeBase implements Serializable {
    public Point[] points;
    public Line[] lines;
    
    public Polygon(Point[] points){
        this.points = points;
        this.lines = new Line[points.length-1];
        for(int i = 0; i<points.length -1; i++){
            lines[i] = new Line(points[i], points[i+1]);
        }
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
    public ArrayList<ShapeBase> components() {
        ArrayList<ShapeBase> list = new ArrayList<ShapeBase>();
        for(Line l : lines){
            list.add(l);
        }
        return list;
    }
}
