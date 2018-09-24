package drawing;

import java.util.ArrayList;
import shapesBase.*;

public class DragPoint {
    public double x;
    public double y;
    public boolean highlight;
    public ArrayList<Binding>  bindings;
    public int highlight_index;
    
    public DragPoint(double x, double y) {
        this.x = x;
        this.y = y;
        highlight = false;
        bindings = new ArrayList<Binding>();
        highlight_index = 0;
    }
    
    public void addBinding(Binding binding){
        bindings.add(binding);
    }
    
    public void light(){
        highlight = true;
        highlight_index = 0;
    }
    
    public void next(){
        if(highlight_index == bindings.size()-1){
            highlight_index = 0;
        }
        else{
            highlight_index++;
        }
    }
    
    public boolean cont(double x, double y, double r){
        return dist(x, y) <= r;
    }
    
    public double dist(double x, double y){
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
    }
    
    public double dist(Binding b){
        return Math.sqrt(Math.pow(b.x - this.x, 2) + Math.pow(b.y - this.y, 2));
    }
    
    public boolean contains(ShapeBase shape){
        for(Binding b : bindings){
            if(b.shape == shape){
                return true;
            }
        }
        return false;
    }
}
