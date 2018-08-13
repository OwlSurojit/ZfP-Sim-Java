package shapesBase;

import geometry.Point;
import structures.StructDrawingInfo;
import java.util.ArrayList;

public abstract class ShapeBase {
    public StructDrawingInfo drawingInfo;
    
    public abstract ArrayList<ShapeBase> getComponents();
    public abstract ArrayList<Point> getDragPoints();
}
