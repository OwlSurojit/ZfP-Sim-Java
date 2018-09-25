package drawing;

import shapesBase.*;
import geometry.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.*;
import java.util.ArrayList;
import zfP_Sim.BodyWindow;


public class DrawPanel extends javax.swing.JPanel{
    public ArrayList<Shape> shapes;
    
    public BodyWindow main;
    public ArrayList<ShapeBase> tempShapes;
    public double[][] raytracer;
    
    public boolean paintBody;
    public boolean paintTemp;
    public boolean paintRaytracer;
    public boolean paintDragPoints;
    
    public int delay;
    
    
    public DrawPanel(){        
        paintBody = false;
        paintTemp = false;
        paintRaytracer = false;
        paintDragPoints = false;
        
        delay = 0;
    }
    
    public void simulate(double[][] tracer){
        raytracer = tracer;
        
        paintBody = true;
        paintTemp = false;
        paintRaytracer = true;
        paintDragPoints = false;
        
        repaint();
    }
    
    public void drawBody(){
        paintBody = true;
        paintTemp = false;
        paintRaytracer = false;
        paintDragPoints = false;
        
        repaint();
    }
    
    public void drawBody_Edit(){
        paintBody = true;
        paintTemp = false;
        paintRaytracer = false;
        paintDragPoints = true;
        
        repaint();
    }
    
    public void drawBody_Edit(ArrayList<ShapeBase> temp){
        tempShapes = temp;
        
        paintBody = true;
        paintTemp = true;
        paintRaytracer = false;
        paintDragPoints = true;
        
        repaint();
    }
    
    public void drawClear(){
        paintBody = false;
        paintTemp = false;
        paintRaytracer = false;
        paintDragPoints = false;
        
        repaint();
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        if(paintBody){
            for(ShapeBase shape : main.body.outline){
                paintShape(g2d, shape);
            }
            for(ShapeBase shape : main.body.defects){
                paintShape(g2d, shape);
            }
        }
        
        if(paintTemp){
            for(ShapeBase shape : tempShapes){
                paintShape(g2d, shape);
            }
        }
        
        if(paintRaytracer){
            g2d.setColor(Color.BLACK);
            for(int i=0; i<raytracer.length-1; i++){
                g2d.draw(new Line2D.Double(raytracer[i][0], raytracer[i][1], raytracer[i+1][0], raytracer[i+1][1]));
            }
            paintNode(g2d, raytracer[0][0], raytracer[0][1], 3, Color.RED);
            for(int i=1; i<raytracer.length; i++){
                paintNode(g2d, raytracer[i][0], raytracer[i][1], 2, Color.BLACK);
            }
        }
        
        if(paintDragPoints){
            for(DragPoint dragPoint : main.body.dragPoints){
                if(dragPoint.highlight){
                    paintNode(g2d, dragPoint.x, dragPoint.y, 3, Color.GREEN);
                    paintShapeLit(g2d, dragPoint.bindings.get(dragPoint.highlight_index).shape);
                }
                else{
                    paintNode(g2d, dragPoint.x, dragPoint.y, 3, Color.BLUE);
                }
            }
        }
        
        g2d.dispose();
    }
    
    public void paintShape(Graphics2D g2d, ShapeBase shape){
        if(shape instanceof Line){
            g2d.setColor(shape.drawingInfo.lineColor);
            g2d.draw(getLine2D((Line) shape));
        }
        else if (shape instanceof Polygon){
            if ( shape.drawingInfo.fill && ((Polygon) shape).closed() ){
                g2d.setColor(shape.drawingInfo.fillColor);
                g2d.fillPolygon(getPolygon2D((Polygon) shape));
            }
            
            g2d.setColor(shape.drawingInfo.lineColor);
            for(Line line : ((Polygon) shape).lines){
                g2d.draw(getLine2D(line));
            }
        }
        else if(shape instanceof Circle){
            Ellipse2D.Double circle = getCircle2D((Circle) shape); 
            if( shape.drawingInfo.fill ){
                g2d.setColor(shape.drawingInfo.fillColor);
                g2d.fill(circle);
            }
            g2d.setColor(shape.drawingInfo.lineColor);
            g2d.draw(circle);
        }
        else if(shape instanceof CircleArc){
            g2d.setColor(shape.drawingInfo.lineColor);
            g2d.draw(getCircleArc2D((CircleArc) shape));
        }
        else if (shape instanceof Oval){
            
            
            /*g2d.rotate((new Vector(1, 0)).getDirAngle((new Line(((Oval)shape).p1, ((Oval)shape).p2).toVector())));
            g2d.draw(oval);*/
            
            //AffineTransform at = AffineTransform.getTranslateInstance(oval.x, oval.y);
            Point P1 = ((Oval)shape).p1;
            Point P2 = ((Oval)shape).p2;
            //AffineTransform at = AffineTransform.getTranslateInstance((P1.x + P2.x)/2, (P1.y + P2.y)/2);
            //at.rotate(-Math.toRadians((new Vector(1, 0)).getDirAngle((new Line(P1, P2).toVector()))));
            AffineTransform at = new AffineTransform();
            at.setToRotation(-Math.toRadians((new Vector(1, 0)).getDirAngle((new Line(P1, P2).toVector()))), (P1.x + P2.x)/2, (P1.y + P2.y)/2);
            Ellipse2D.Double oval = getOval2D((Oval) shape);
            g2d.setColor(shape.drawingInfo.lineColor);
            if (shape.drawingInfo.fill){
                g2d.setColor(shape.drawingInfo.fillColor);
                g2d.fill(at.createTransformedShape(oval));
            }
            g2d.draw(at.createTransformedShape(oval));
            
        }
    }
    
    public void paintShapeLit(Graphics2D g2d, ShapeBase shape){
        if(shape instanceof Line){
            g2d.setColor(shape.drawingInfo.lineColorLit);
            g2d.draw(getLine2D((Line) shape));
        }
        else if (shape instanceof Polygon){
            if ( shape.drawingInfo.fill && ((Polygon) shape).closed() ){
                g2d.setColor(shape.drawingInfo.fillColor);
                g2d.fillPolygon(getPolygon2D((Polygon) shape));
            }
            
            g2d.setColor(shape.drawingInfo.lineColorLit);
            for(Line line : ((Polygon) shape).lines){
                g2d.draw(getLine2D(line));
            }
        }
        else if(shape instanceof Circle){
            Ellipse2D.Double circle = getCircle2D((Circle) shape); 
            if( shape.drawingInfo.fill ){
                g2d.setColor(shape.drawingInfo.fillColor);
                g2d.fill(circle);
            }
            g2d.setColor(shape.drawingInfo.lineColorLit);
            g2d.draw(circle);
        }
        else if(shape instanceof CircleArc){
            g2d.setColor(shape.drawingInfo.lineColorLit);
            g2d.draw(getCircleArc2D((CircleArc) shape));
        }
        else if (shape instanceof Oval){
            
            
            /*g2d.rotate((new Vector(1, 0)).getDirAngle((new Line(((Oval)shape).p1, ((Oval)shape).p2).toVector())));
            g2d.draw(oval);*/
            
            //AffineTransform at = AffineTransform.getTranslateInstance(oval.x, oval.y);
            Point P1 = ((Oval)shape).p1;
            Point P2 = ((Oval)shape).p2;
            //AffineTransform at = AffineTransform.getTranslateInstance((P1.x + P2.x)/2, (P1.y + P2.y)/2);
            //at.rotate(-Math.toRadians((new Vector(1, 0)).getDirAngle((new Line(P1, P2).toVector()))));
            AffineTransform at = new AffineTransform();
            at.setToRotation(-Math.toRadians((new Vector(1, 0)).getDirAngle((new Line(P1, P2).toVector()))), (P1.x + P2.x)/2, (P1.y + P2.y)/2);
            Ellipse2D.Double oval = getOval2D((Oval) shape);
            g2d.setColor(shape.drawingInfo.lineColorLit);
            if (shape.drawingInfo.fill){
                g2d.setColor(shape.drawingInfo.fillColor);
                g2d.fill(at.createTransformedShape(oval));
            }
            g2d.draw(at.createTransformedShape(oval));
            
        }
    }
    
    public void paintNode(Graphics2D g2d, double x, double y, double radius, Color color){
        Ellipse2D.Double node = new Ellipse2D.Double();
        node.x = x - radius;
        node.y = y - radius;
        node.width = 2 * radius;
        node.height = 2 * radius;
        
        g2d.setColor(color);
        g2d.fill(node);
    }
    
    public Line2D.Double getLine2D(Line line){
        Line2D.Double line2D = new Line2D.Double();
        line2D.x1 = line.start.x;
        line2D.y1 = line.start.y;
        line2D.x2 = line.end.x;
        line2D.y2 = line.end.y;
        
        return line2D;
    }
    
    public java.awt.Polygon getPolygon2D(Polygon polygon){
        java.awt.Polygon polygon2D = new java.awt.Polygon();
        for(Point point : polygon.points){
            polygon2D.addPoint((int)point.x, (int)point.y);
        }
        
        return polygon2D;
    }
    
    public Ellipse2D.Double getCircle2D(Circle circle){
        Ellipse2D.Double circle2D = new Ellipse2D.Double();
        circle2D.x = circle.center.x -circle.radius;
        circle2D.y = circle.center.y -circle.radius;
        circle2D.width = 2*circle.radius;
        circle2D.height = 2*circle.radius;
        
        return circle2D;
    }
    
    public Arc2D.Double getCircleArc2D(CircleArc circleArc){
        Arc2D.Double circleArc2D = new Arc2D.Double();
        circleArc2D.x = circleArc.center.x -circleArc.radius;
        circleArc2D.y = circleArc.center.y -circleArc.radius;
        circleArc2D.width = 2*circleArc.radius;
        circleArc2D.height = 2*circleArc.radius;
        circleArc2D.start = circleArc.offsetangle;
        circleArc2D.extent = circleArc.arcangle;
        
        return circleArc2D;
    }
    
    public Ellipse2D.Double getOval2D(Oval oval){
        Ellipse2D.Double oval2d = new Ellipse2D.Double();
        oval2d.width = oval.e;
        oval2d.height = Math.sqrt(Math.pow(oval.e/2, 2)-Math.pow((new Line(oval.p1, oval.p2)).length()/2, 2));
        oval2d.x = (oval.p1.x + oval.p2.x - oval2d.width)/2;
        oval2d.y = (oval.p1.y + oval.p2.y - oval2d.height)/2;
        return oval2d;
    }
}
