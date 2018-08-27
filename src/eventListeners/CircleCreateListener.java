package eventListeners;

import geometry.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JToggleButton;
import shapesBase.*;
import drawing.DrawPanel;

public class CircleCreateListener implements MouseListener{

    Point center;
    Point peripher;
    DrawPanel drawPanel;
    
    public CircleCreateListener(DrawPanel drawPanel){
        center = null;
        peripher = null;
        this.drawPanel = drawPanel;
    }
    

    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getButton() == 1){
            if (center == null) {
                center = new Point(me.getX(), me.getY());
                //ArrayList<ShapeBase> temp = new ArrayList<ShapeBase>();
                //temp.add(center);
                //drawPanel.drawBody_Edit(temp);
                // hier bitte Punkt einzeichnen                
            } else if (peripher == null) {
                peripher = new Point(me.getX(), me.getY());
                Circle circle = new Circle(center, Line(center, peripher).length());
                ArrayList<ShapeBase> sb = new ArrayList<ShapeBase>();
                sb.add(circle);
                drawPanel.main.body.addDefect(sb);
                drawPanel.drawBody_Edit();
            }
        }
        if(me.getButton() == 3){
            center = null;
            peripher = null;
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
