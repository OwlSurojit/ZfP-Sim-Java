package structures;

import java.awt.Color;

public class StructDrawingInfo {
    public Color lineColor;
    public boolean fill;
    public Color fillColor;
    
    public StructDrawingInfo(){
        lineColor = Color.BLACK;
        fill = false;
        fillColor = Color.GRAY;
    }
    
    public StructDrawingInfo(Color lcolor) {
        lineColor = lcolor;
        fill = false;
        fillColor = Color.GRAY;
    }
    
    public StructDrawingInfo(boolean fill){
        lineColor = Color.BLACK;
        this.fill = fill;
        fillColor = Color.GRAY;
    }
    
    public StructDrawingInfo(Color lcolor, boolean fill, Color fcolor) {
        lineColor = lcolor;
        this.fill = fill;
        fillColor = fcolor;
    }
}
