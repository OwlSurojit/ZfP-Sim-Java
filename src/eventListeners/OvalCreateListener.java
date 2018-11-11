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

public class OvalCreateListener implements MouseListener{

    public Point P1, P2;
    Double e;
    DrawPanel drawPanel;
    
    public OvalCreateListener(DrawPanel drawPanel){
        this.P1 = null;
        this.P2 = null;
        this.e = null;
        this.drawPanel = drawPanel;
    }
    
    public void exactInput(double x, double y) {
        if (this.P1 == null) {
            this.P1 = new Point(x, y);
        }
        else {
            this.P2 = new Point(x, y);
        }
    }
    
    public void exactInput(double e) {
        Oval oval = new Oval(this.P1, this.P2, e);
        if(((EditorWindow)drawPanel.main).outlineAddRadioButton.isSelected()){
            drawPanel.main.body.addOutline(oval);
        }
        else{
            drawPanel.main.body.addDefect(oval);
        }
        this.P1 = null;
        this.P2 = null;
        this.e = null;
        drawPanel.drawBody_Edit();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == 1) {
            if (this.P1 == null) {
                this.P1 = new Point(me.getX(), me.getY());
            }
            else if (this.P2 == null) {
                this.P2 = new Point(me.getX(), me.getY());
            }
            else {
                Point temp = new Point(me.getX(), me.getY());
                this.e = (new Line(this.P1, temp)).length() + (new Line(this.P2, temp)).length();
                Oval oval = new Oval(this.P1, this.P2, this.e);
                if(((EditorWindow)drawPanel.main).outlineAddRadioButton.isSelected()){
                    drawPanel.main.body.addOutline(oval);
                }
                else{
                    drawPanel.main.body.addDefect(oval);
                }
                this.P1 = null;
                this.P2 = null;
                this.e = null;
                drawPanel.drawBody_Edit();
            }
        }
        else if (me.getButton() == 3) {
            this.P1 = null;
            this.P2 = null;
            this.e = null;
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
