package eventListeners;

import geometry.Point;
import geometry.Ray;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JToggleButton;
import shapesBase.*;
import drawing.DrawPanel;
import control.Raytracer;
import zfP_Sim.EditorWindow;

public class RectangleCreateListener implements MouseListener{

    Point start, corner1, corner2;
    DrawPanel drawPanel;
    
    public RectangleCreateListener(DrawPanel drawPanel){
        this.start = null;
        this.corner1 = null;
        this.corner2 = null;
        this.drawPanel = drawPanel;
    }
    
    public void exactInput(double x, double y) {
        if (start == null){
            start = new Point(x, y);
            Point[] points = {start};
            ArrayList<ShapeBase> sb = new ArrayList<ShapeBase>();
            sb.add(new Polygon(points));
            drawPanel.drawBody_Edit(sb);
        }else if (corner1 == null){
            corner1 = new Point(x, y);
            Point[] points = {start, corner1};
            ArrayList<ShapeBase> sb = new ArrayList<ShapeBase>();
            sb.add(new Polygon(points));
            drawPanel.drawBody_Edit(sb);
        }else if(corner2 == null){
            Point p = new Point(x, y);
            Point sp = Raytracer.getLineIntersection(new Ray(p, (new Line(start, corner1).toVector().toNormal())), new Line(start, corner1)).point;
            corner2 = (new Ray(start, (new Line(sp, p)).toVector())).getPoint(1);
            Point corner3 = (new Ray(corner1, (new Line(sp, p)).toVector())).getPoint(1);
            Point[] points = {start, corner1, corner3, corner2, start};
            if(((EditorWindow)drawPanel.main).outlineAddRadioButton.isSelected()){
                drawPanel.main.body.addOutline(new Polygon(points));
            }
            else{
                drawPanel.main.body.addDefect(new Polygon(points));
            }
            this.start = null;
            this.corner1 = null;
            this.corner2 = null;
            drawPanel.drawBody_Edit();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getButton() == 1){
            if (start == null){
                start = new Point(me.getX(), me.getY());
                Point[] points = {start};
                ArrayList<ShapeBase> sb = new ArrayList<ShapeBase>();
                sb.add(new Polygon(points));
                drawPanel.drawBody_Edit(sb);
            }else if (corner1 == null){
                corner1 = new Point(me.getX(), me.getY());
                Point[] points = {start, corner1};
                ArrayList<ShapeBase> sb = new ArrayList<ShapeBase>();
                sb.add(new Polygon(points));
                drawPanel.drawBody_Edit(sb);
            }else if(corner2 == null){
                Point p = new Point(me.getX(), me.getY());
                Point sp = Raytracer.getLineIntersection(new Ray(p, (new Line(start, corner1).toVector().toNormal())), new Line(start, corner1)).point;
                corner2 = (new Ray(start, (new Line(sp, p)).toVector())).getPoint(1);
                Point corner3 = (new Ray(corner1, (new Line(sp, p)).toVector())).getPoint(1);
                Point[] points = {start, corner1, corner3, corner2, start};
                if(((EditorWindow)drawPanel.main).outlineAddRadioButton.isSelected()){
                    drawPanel.main.body.addOutline(new Polygon(points));
                }
                else{
                    drawPanel.main.body.addDefect(new Polygon(points));
                }
                this.start = null;
                this.corner1 = null;
                this.corner2 = null;
                drawPanel.drawBody_Edit();
            }
        }
        if(me.getButton() == 3){
            this.start = null;
            this.corner1 = null;
            this.corner2 = null;
            drawPanel.drawBody_Edit();
        }
        if(me.getButton() == 2){
            // zeichnet bei existierendem Startpunkt ein waagerechts Rechteck aus 2 Punkten
            if(this.start != null && me.getX() != this.start.x && me.getY() != this.start.y){
                Point diagonal = new Point(me.getX(), me.getY());
                Point p1 = new Point(this.start.x, diagonal.y);
                Point p2 = new Point(diagonal.x, this.start.y);
                Point[] points = {this.start, p1, diagonal, p2, this.start};
                if(((EditorWindow)drawPanel.main).outlineAddRadioButton.isSelected()){
                    drawPanel.main.body.addOutline(new Polygon(points));
                }
                else{
                    drawPanel.main.body.addDefect(new Polygon(points));
                }
                this.start = null;
                this.corner1 = null;
                this.corner2 = null;
                drawPanel.drawBody_Edit();
                
                
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        // passes
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // passes
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        // passes
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // passes
    }
    
}
