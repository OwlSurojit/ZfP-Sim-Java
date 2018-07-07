package shapesDraw;

import java.awt.Color;
import shapesBase.Circle;

public class CircleDraw extends java.awt.geom.Ellipse2D.Double{
    
    public Color color;
    
    public CircleDraw(Circle circle, Color color){
        this.x = circle.center.x -circle.radius;
        this.y = circle.center.y -circle.radius;
        this.width = 2*circle.radius;
        this.height = 2*circle.radius;
        this.color = color;
    }
    
    public CircleDraw(Circle circle){
        this.x = circle.center.x -circle.radius;
        this.y = circle.center.y -circle.radius;
        this.width = 2*circle.radius;
        this.height = 2*circle.radius;
        this.color = Color.BLACK;
    }
}
