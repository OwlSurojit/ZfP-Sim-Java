package shapesDraw;

import java.awt.Color;
import shapesBase.Line;

public class LineDraw extends java.awt.geom.Line2D.Double{
    
    public Color color;
    
    public LineDraw(Line line, Color color){
        this.x1 = line.start.x;
        this.y1 = line.start.y;
        this.x2 = line.end.x;
        this.y2 = line.end.y;
        this.color = color;
    }
    
    public LineDraw(Line line){
        this.x1 = line.start.x;
        this.y1 = line.start.y;
        this.x2 = line.end.x;
        this.y2 = line.end.y;
        this.color = Color.BLACK;
    }
}
