package zfP_Sim;

import control.Body;
import shapesBase.ShapeBase;


public abstract class BodyWindow extends javax.swing.JFrame {
    public Body body;
    public ShapeBase lit;
    
    public abstract void setLit(ShapeBase shape);
    public abstract void outlineChanged();
}
