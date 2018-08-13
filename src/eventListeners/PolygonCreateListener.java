package eventListeners;

import geometry.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JToggleButton;
import shapesBase.*;
import drawing.DrawPanel;

public class PolygonCreateListener implements MouseListener{

    ArrayList<Point> currentPolygon;
    DrawPanel drawPanel;
    
    public PolygonCreateListener(DrawPanel drawPanel){
        currentPolygon = new ArrayList<Point>();
        this.drawPanel = drawPanel;
    }
    

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getButton() == 1){
            Point p1 = new Point(me.getX(), me.getY());
            currentPolygon.add(p1);
            
            Point[] points = new Point[currentPolygon.size()];
            ArrayList<ShapeBase> temp = new ArrayList<ShapeBase>();
            temp.add( new Polygon( currentPolygon.toArray(points)) );
            drawPanel.drawBody_Edit(temp);
        }
        if(me.getButton() == 3){
            currentPolygon = new ArrayList<Point>();
            drawPanel.drawBody_Edit();
        }
        if(me.getButton() == 2){
            if(currentPolygon.size() > 1){
                Point[] points = new Point[currentPolygon.size()];
                drawPanel.main.body.addDefect(new Polygon( currentPolygon.toArray(points)));
            }
            currentPolygon = new ArrayList<Point>();
            drawPanel.drawBody_Edit();
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