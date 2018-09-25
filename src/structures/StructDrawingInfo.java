package structures;

import java.awt.Color;

public class StructDrawingInfo {
    public String name;
    public Color lineColor;
    public Color lineColorLit;
    public boolean fill;
    public Color fillColor;
    
    public StructDrawingInfo(){
        name = "";
        lineColor = Color.BLACK;
        lineColorLit = Color.RED;
        fill = false;
        fillColor = Color.GRAY;
    }
    
    public StructDrawingInfo(Color lcolor) {
        lineColor = lcolor;
        lineColorLit = Color.RED;
        fill = false;
        fillColor = Color.GRAY;
    }
    
    public StructDrawingInfo(boolean fill){
        lineColor = Color.BLACK;
        lineColorLit = Color.RED;
        this.fill = fill;
        fillColor = Color.GRAY;
    }
    
    public StructDrawingInfo(Color lcolor, boolean fill, Color fcolor) {
        lineColor = lcolor;
        lineColorLit = Color.RED;
        this.fill = fill;
        fillColor = fcolor;
    }
}
