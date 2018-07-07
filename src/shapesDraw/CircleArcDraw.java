package shapesDraw;

import java.awt.Color;
import shapesBase.CircleArc;

public class CircleArcDraw extends java.awt.geom.Arc2D.Double{
    
    public Color color;
    
    public CircleArcDraw(CircleArc carc, Color color){
        this.x = carc.center.x -carc.radius;
        this.y = carc.center.y -carc.radius;
        this.width = 2*carc.radius;
        this.height = 2*carc.radius;
        this.start = carc.offsetangle;
        this.extent = carc.arcangle;
        this.color = color;
    }
    
    public CircleArcDraw(CircleArc carc){
        this.x = carc.center.x -carc.radius;
        this.y = carc.center.y -carc.radius;
        this.width = 2*carc.radius;
        this.height = 2*carc.radius;
        this.start = carc.offsetangle;
        this.extent = carc.arcangle;
        this.color = Color.BLACK;
    }
}
