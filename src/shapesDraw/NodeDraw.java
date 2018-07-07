package shapesDraw;

import java.awt.Color;

public class NodeDraw extends java.awt.geom.Ellipse2D.Double{
    
    public Color color;
    
    public NodeDraw(double x, double y, double r, Color color){
        this.x = x-r;
        this.y = y-r;
        this.width = 2*r;
        this.height = 2*r;
        this.color = color;
    }
    
    public NodeDraw(double x, double y, double r){
        this.x = x-r;
        this.y = y-r;
        this.width = 2*r;
        this.height = 2*r;
        this.color = Color.BLACK;
    }
}
