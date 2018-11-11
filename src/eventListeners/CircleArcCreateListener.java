/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventListeners;


import geometry.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JToggleButton;
import shapesBase.*;
import drawing.DrawPanel;
import zfP_Sim.EditorWindow;

/**
 *
 * @author morit
 */
public class CircleArcCreateListener implements MouseListener{
    Point center;
    Point start;
    Point end;
    DrawPanel drawPanel;
    
    public CircleArcCreateListener(DrawPanel drawPanel){
        center = null;
        start = null;
        end = null;
        this.drawPanel = drawPanel;
    }
    
    public void exactInput(double x, double y) {
        if (center == null) {
            center = new Point(x, y);
            //ArrayList<ShapeBase> temp = new ArrayList<ShapeBase>();
            //temp.add(center);
            //drawPanel.drawBody_Edit(temp);
            // hier bitte Punkt einzeichnen
        } else if (start == null) {
            start = new Point(x, y);
        }else if(end == null){
            end = new Point(x, y);
            CircleArc arc = new CircleArc(center, start, end);
            if(((EditorWindow)drawPanel.main).outlineAddRadioButton.isSelected()){
                drawPanel.main.body.addOutline(arc);
            }
            else{
                drawPanel.main.body.addDefect(arc);
            }
            center = null;
            start = null;
            end = null;
            drawPanel.drawBody_Edit();
        }
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
            } else if (start == null) {
                start = new Point(me.getX(), me.getY());
            }else if(end == null){
                end = new Point(me.getX(), me.getY());
                CircleArc arc = new CircleArc(center, start, end);
                if(((EditorWindow)drawPanel.main).outlineAddRadioButton.isSelected()){
                    drawPanel.main.body.addOutline(arc);
                }
                else{
                    drawPanel.main.body.addDefect(arc);
                }
                center = null;
                start = null;
                end = null;
                drawPanel.drawBody_Edit();
            }
        }
        if(me.getButton() == 3){
            center = null;
            start = null;
            end = null;
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
