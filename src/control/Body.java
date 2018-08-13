package control;

import shapesBase.ShapeBase;
import shapesBase.CircleArc;
import shapesBase.Polygon;
import shapesBase.Circle;
import geometry.Point;
import java.io.Serializable;
import java.util.ArrayList;
import drawing.DragPoint;

public class Body implements Serializable {
    public ArrayList<ShapeBase> outline;
    public ArrayList<ShapeBase> defects;
    
    public Body(){
        outline = new ArrayList();
        defects = new ArrayList();
    }
    
    public void addOutline(ShapeBase segment){
        outline.add(segment);
    }
    
    public void addDefect(ShapeBase defect){
        defects.add(defect);
    }
    
    public void addDefect(ArrayList<ShapeBase> defect){
        defects.addAll(defect);
    }
    
    public void exampleOne(){
        outline = new ArrayList();
        defects = new ArrayList();
        // Ein 2*2-Quadrat, ohne Fehler. Von oben runter sollte das eine Reflektion an der Rueckwand geben
        outline.add(new Polygon( new Point[]{ new Point(100.0,100.0), new Point(300.0,100.0), new Point(300.0,300.0), new Point(100.0,300.0), new Point(100.0,100.0) } ) );
    }
    
    public void exampleTwo(){
        outline = new ArrayList();
        defects = new ArrayList();
        // Ein 3*3-Quadrat, ein 1*1-Quadrat als Fehler in der Mitte
        outline.add(new Polygon( new Point[]{ new Point(100.0,100.0), new Point(400.0,100.0), new Point(400.0,400.0), new Point(100.0,400.0), new Point(100.0,100.0) } ) );
        defects.add(new Polygon( new Point[]{ new Point(200.0,200.0), new Point(300.0,200.0), new Point(300.0,300.0), new Point(200.0,300.0), new Point(200.0,200.0) } ) );
    }
    
    public void exampleThree(){
        outline = new ArrayList();
        defects = new ArrayList();
        // Ein 3*3-Quadrat, die am weitesten vom Koordinatenursprung entfernte Ecke ist durch einen nach innen gewölbten Viertelkreis mit dem Radius 1 und der Ecke als Zentrum ersetzt
        outline.add(new Polygon( new Point[]{ new Point(100.0,100.0), new Point(400.0,100.0), new Point(400.0,300.0) } ) );
        outline.add(new CircleArc( new Point(400.0,400.0), 100, 90, 90) );
        outline.add(new Polygon( new Point[]{ new Point(300.0,400.0), new Point(100.0,400.0), new Point(100.0,100.0) } ) );
    }
    
    public void exampleFour(){
        outline = new ArrayList();
        defects = new ArrayList();
        // Ein 3*3-Quadrat, ein Radius-0.5-Kreis als Fehler in der Mitte
        outline.add(new Polygon( new Point[]{ new Point(100.0,100.0), new Point(400.0,100.0), new Point(400.0,400.0), new Point(100.0,400.0), new Point(100.0,100.0) } ) );
        defects.add(new Circle( new Point(250.0, 250.0), 50.0));
    }
    
    public void exampleLongBar(){
        outline = new ArrayList();
        defects = new ArrayList();
        // Ein 3*3-Quadrat, ein 1*1-Quadrat als Fehler in der Mitte
        outline.add(new Polygon( new Point[]{ new Point(30.0,30.0), new Point(1750.0,30.0), new Point(1750.0,630.0), new Point(30.0,630.0), new Point(30.0,30.0) } ) );
        defects.add(new Polygon( new Point[]{ new Point(830.0,570.0), new Point(890.0,570.0), new Point(890.0,600.0), new Point(830.0,600.0), new Point(830.0,570.0) } ) );
        defects.add(new Circle( new Point(1290.0, 585.0), 30.0));
    }
    
    public void exampleWheelClear(){
        outline = new ArrayList();
        defects = new ArrayList();
        // Ein r=150-Kreis, r=50-Kreis-Defekt
        outline.add( new Circle( new Point(890.0, 330.0), 300.0) );
        defects.add( new Circle( new Point(890.0, 330.0), 250.0) );
    }
    
    public void exampleWheelDefect(){
        outline = new ArrayList();
        defects = new ArrayList();
        // Ein r=150-Kreis, r=50-Kreis-Defekt
        outline.add( new Circle( new Point(890.0, 330.0), 300.0) );
        defects.add( new Circle( new Point(890.0, 330.0), 250.0) );
        defects.add( new Polygon( new Point[]{ new Point(600,315.0), new Point(630.0,315.0), new Point(630.0,345.0), new Point(600.0,345.0), new Point(600.0,315.0) } ) );
    }
    
    public ArrayList<ShapeBase> components() {
        ArrayList<ShapeBase> list = new ArrayList<ShapeBase>();
        for(ShapeBase s : outline){
            list.addAll(s.getComponents());
        }
        for(ShapeBase s : defects){
            list.addAll(s.getComponents());
        }
        list.addAll((new Polygon( new Point[]{ new Point(3.0,3.0), new Point(1771.0,3.0), new Point(1771.0,654.0), new Point(3.0,654.0), new Point(3.0,3.0) } )).getComponents());
        return list;
    }
    
    public ArrayList<DragPoint> getDragPoints(){
        ArrayList<DragPoint> dragPoints = new ArrayList<DragPoint>();
        for(ShapeBase shape : defects){
            ArrayList<Point> points = shape.getDragPoints();
            for(Point p : points){
                boolean merged = false;
                for(DragPoint dp : dragPoints){
                    if(p.dist(dp) < 1){
                        if(! dp.shapes.contains(shape)){
                            dp.addShape(shape);
                        }
                        merged = true;
                        break;
                    }
                }
                if(!merged){
                    DragPoint dragPoint = new DragPoint(p.x, p.y);
                    dragPoint.addShape(shape);
                    dragPoints.add(dragPoint);
                }
            }
        }
        return dragPoints;
    }
}
