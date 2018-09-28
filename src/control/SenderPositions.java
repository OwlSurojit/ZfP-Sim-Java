package control;

import drawing.DrawPanel;
import geometry.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import shapesBase.Circle;
import shapesBase.CircleArc;
import shapesBase.Line;
import shapesBase.Oval;
import shapesBase.Polygon;
import shapesBase.ShapeBase;

public class SenderPositions {
    public static ArrayList<double[]> getPathPoints(Body body){
        Area concat = new Area();
        for(ShapeBase shape : body.outline){
            concat.add(new Area(geomShape(shape)));
        }
        
        FlatteningPathIterator iter = new FlatteningPathIterator(concat.getPathIterator(new AffineTransform()), 0.1, 100);
        ArrayList<double[]> segments = new ArrayList<>();
        double[] coords = new double[6];
        while(! iter.isDone()){
            iter.currentSegment(coords);
            segments.add(new double[]{coords[0], coords[1]});
            iter.next();
        }
        ArrayList<double[]> points = new ArrayList<>();
        for(int i = 0; i<segments.size()-1; i++){
            // TODO:    Break up long segments into many small ones
            //          Remove last point (see index end of for loop)
            //          consider minDist; precision of Iterator
        }
        
        if(points.isEmpty()){
            points.add(new double[]{30, 30});
        }
        return segments;
    }
    
    public static java.awt.Shape geomShape(ShapeBase shape){
        if(shape instanceof Line){
            return DrawPanel.getLine2D((Line) shape);
        }
        else if (shape instanceof Polygon){
            return DrawPanel.getPolygon2D((Polygon) shape);
        }
        else if(shape instanceof Circle){
            return DrawPanel.getCircle2D((Circle) shape); 
        }
        else if(shape instanceof CircleArc){
            return DrawPanel.getCircleArc2D((CircleArc) shape); 
        }
        else if (shape instanceof Oval){
            return DrawPanel.getOval2D((Oval) shape); 
        }
        else return null;
    }
}
