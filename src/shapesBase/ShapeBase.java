package shapesBase;

import drawing.Binding;
import java.io.Serializable;
import structures.StructDrawingInfo;
import java.util.ArrayList;

public abstract class ShapeBase implements Serializable {
    public StructDrawingInfo drawingInfo;
    
    public abstract ArrayList<ShapeBase> getComponents();
    public abstract ArrayList<Binding> getDragPoints();
    public abstract void refactor(Binding bind, double nx, double ny);
    public abstract void rotate(double degree);
}
