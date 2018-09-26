package shapesBase;

import drawing.Binding;
import enums.bindType;
import geometry.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
        return points[0].equals(points[points.length-1]);
    }
    
    @Override
    public String toString(){
        String temp = "Polygon(";
        for(Line l : lines){
            temp += l + ", ";
        }
        return temp.substring(0, temp.length()-2) + ")";
    }
    
    public Point getCenter(){
        ArrayList<Point> points = new ArrayList<Point>(Arrays.asList(this.points));
        if(closed()){
            points.remove(points.size()-1);
        }
        return Point.center(points);
    }
    
    public int getPoints(double x, double y){
        for(int i = 0; i<points.length; i++){
            if(points[i].x == x && points[i].y == y){
                return i;
            }
        }
        return -1;
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
    public ArrayList<Binding> getDragPoints(){
        ArrayList<Binding> list = new ArrayList<Binding>();
        for(Point p : points){
            list.add(new Binding(p, this, bindType.POLY_POINT));
        }
        if(closed()){
            list.remove(list.size()-1);
        }
        list.add(new Binding(getCenter(), this, bindType.POLY_CENTER));
        return list;
    }
    
    @Override
    public void refactor(Binding bind, double nx, double ny){
        switch(bind.type){
            case POLY_CENTER:
                Point c = getCenter();
                double tx = (nx-c.x);
                double ty = (ny-c.y);
                for(int i = 0; i<points.length; i++){
                    points[i] = new Point(points[i].x + tx, points[i].y + ty);
                }
                this.lines = new Line[points.length-1];
                for(int i = 0; i<points.length -1; i++){
                    lines[i] = new Line(points[i], points[i+1]);
                }
                break;
            case POLY_POINT:
                if(closed() && bind.x == points[0].x && bind.y == points[0].y){
                    points[0] = new Point(nx, ny);
                    points[points.length-1] = new Point(nx, ny);
                }
                else{
                    int i = getPoints(bind.x, bind.y);
                    if(i != -1){
                        points[i] = new Point(nx, ny);
                    }
                }
                this.lines = new Line[points.length-1];
                for(int i = 0; i<points.length -1; i++){
                    lines[i] = new Line(points[i], points[i+1]);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void rotate(double degree) {
        Point c = getCenter();
        for(int i = 0; i<points.length; i++){
            points[i].rotateAround(c, degree);
        }
        this.lines = new Line[points.length-1];
        for(int i = 0; i<points.length -1; i++){
            lines[i] = new Line(points[i], points[i+1]);
        }
    }
}
