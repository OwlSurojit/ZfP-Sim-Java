package shapesBase;

import drawing.Binding;
import enums.BindType;
import geometry.Point;
import geometry.Vector;
import geometry.Ray;
import java.util.ArrayList;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import structures.StructDrawingInfo;

public class Oval extends ShapeBase{
    public Point p1;
    public Point p2;
    public double e;
    
    public Oval(Point p1, Point p2, double e){
        if (p1.equals(p2)){
            throw new ValueException("P1 and P2 mustn't be identical.");
        }
        else if (e <= p1.dist(p2)){
            throw new ValueException("E must be greater than distance of P1 and P2.");
        }
        else {
            this.p1 = p1;
            this.p2 = p2;
            this.e = e;
            
            this.drawingInfo = new StructDrawingInfo();
        }
    }
    
    @Override
    public String toString(){
        return "Oval(p1 = " + p1 + ", p2 = " + p2 + ", e = " + e + ")";
    }
    
    public Ray getTangent(Point S){
        // Weg ueber Kreis um Oval:
        // Schnittpunkt des Strahls p1 ueber S auf Kreis k = T
        Ray rk = new Ray(this.p1, (new Line(this.p1, S)).toVector());
        Point T = rk.getPoint(this.e/(new Line(this.p1, S).length()));
        // Vector der Tangente als Summe beider (gleic langer) Teilvekoren
        Vector vt = (new Line(this.p2, S)).toVector().add((new Line(T, S)).toVector());
        return new Ray(S, vt);
        
        // Weg ueber Brennpunkteigenschaft (Winkelhalbierende = Normale) (funktioiert auch)
        // Winkel der Normalen zu Bezug (1,0)
        /*double globalNormAngle = Math.toRadians((new Vector(1,0)).getDirAngle((new Line(S, oval.p1)).toVector()) + (new Line(S, oval.p1)).toVector().getAngle((new Line(S, oval.p2).toVector()))/2);
        Vector vt = (new Vector(Math.sin(globalNormAngle), Math.cos(globalNormAngle))).toNormal();
        return new Ray(S, vt);*/
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
        list.add(new Binding(Point.center(p1, p2), this, BindType.OVAL_CENTER));
        list.add(new Binding(p1, this, BindType.OVAL_P1));
        list.add(new Binding(p2, this, BindType.OVAL_P2));
        return list;
    }
    
    @Override
    public void refactor(Binding bind, double nx, double ny){
        switch(bind.type){
            case OVAL_CENTER:
                Point c = Point.center(p1, p2);
                double tx = (nx-c.x);
                double ty = (ny-c.y);
                p1 = new Point(p1.x + tx, p1.y + ty);
                p2 = new Point(p2.x + tx, p2.y + ty);
                break;
            case OVAL_P1:
                Point np1 = new Point(nx, ny);
                if(e > np1.dist(p2)){
                    p1 = np1;
                }
                break;
            case OVAL_P2:
                Point np2 = new Point(nx, ny);
                if(e > np2.dist(p1)){
                    p2 = np2;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void rotate(double degree) {
        Point c = Point.center(p1, p2);
        p1.rotateAround(c, degree);
        p2.rotateAround(c, degree);
    }
}
