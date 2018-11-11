package eventListeners;

import geometry.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JToggleButton;
import shapesBase.*;
import drawing.DrawPanel;
import zfP_Sim.EditorWindow;

public class CircleCreateListener implements MouseListener{

    public Point center;
    public Point peripher;
    public DrawPanel drawPanel;
    
    public CircleCreateListener(DrawPanel drawPanel){
        center = null;
        peripher = null;
        this.drawPanel = drawPanel;
    }
    
    public void exactInput(double x, double y) {
        center = new Point(x, y);
    }
    
    public void exactInput(double r) {
        Circle circle = new Circle(center, r);
        if(((EditorWindow)drawPanel.main).outlineAddRadioButton.isSelected()){
            drawPanel.main.body.addOutline(circle);
        }
        else{
            drawPanel.main.body.addDefect(circle);
        }
        center = null;
        peripher = null;
        drawPanel.drawBody_Edit();
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
                Circle circle = new Circle(center, new Line(center, peripher).length());
                if(((EditorWindow)drawPanel.main).outlineAddRadioButton.isSelected()){
                    drawPanel.main.body.addOutline(circle);
                }
                else{
                    drawPanel.main.body.addDefect(circle);
                }
                center = null;
                peripher = null;
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
