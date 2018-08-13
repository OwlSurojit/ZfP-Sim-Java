package drawing;

import java.util.ArrayList;
import shapesBase.*;

public class DragPoint {
    public double x;
    public double y;
    public boolean highlight;
    public ArrayList<ShapeBase>  shapes;
    public int highlight_index;
    
    public DragPoint(double x, double y) {
        this.x = x;
        this.y = y;
        highlight = false;
        shapes = new ArrayList<ShapeBase>();
        highlight_index = 0;
    }
    
    public void addShape(ShapeBase shape){
        shapes.add(shape);
    }
    
    public void light(){
        highlight = true;
        highlight_index = 0;
    }
    
    public void next(){
        if(highlight_index == shapes.size()-1){
            highlight_index = 0;
        }
        else{
            highlight_index++;
        }
    }
}
