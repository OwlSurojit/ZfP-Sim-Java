package zfP_Sim;

import shapesDraw.*;
import shapesBase.*;
import geometry.Ray;
import geometry.Vector;
import geometry.Point;
import control.Body;
import control.Raytracer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import structures.StructRayObj;


public class SimulationPanel extends javax.swing.JPanel{
    public ArrayList<Shape> shapes;
    public int delay;
    public MainWindow main;
    
    
    public SimulationPanel(){
        shapes = new ArrayList<Shape>(16);
    }
    
    public void simulate(double[][] tracer){
        this.delay = delay;
        shapes = new ArrayList<Shape>(16);
        for(ShapeBase shape : main.body.outline){
            addShapeBase(shape);
        }
        for(ShapeBase shape : main.body.defects){
            addShapeBase(shape);
        }
        addRaytracer(tracer);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        NodeDraw sender = null;
        for (Shape shape : shapes) {
            if(shape instanceof LineDraw){
                g2d.setColor(((LineDraw) shape).color);
                g2d.draw(shape);
            }
            else if(shape instanceof NodeDraw){
                if(sender == null){sender = (NodeDraw) shape;}
                g2d.setColor(((NodeDraw) shape).color);
                g2d.draw(shape);
                g2d.fill(shape);
            }
            else if(shape instanceof CircleDraw){
                g2d.setColor(((CircleDraw) shape).color);
                g2d.draw(shape);
            }
            else if(shape instanceof CircleArcDraw){
                g2d.setColor(((CircleArcDraw) shape).color);
                g2d.draw(shape);
            }
        }
        if(sender != null){
            g2d.setColor(sender.color);
            g2d.fill(sender);
        } 
        g2d.dispose();
    }
    
    public void addBody(){
        clearShapes();
        if(!main.body.outline.isEmpty()){
            for(ShapeBase shape : main.body.outline){
                addShapeBase(shape);
            }
            for(ShapeBase shape : main.body.defects){
                addShapeBase(shape);
            }
            repaint();
        }
    }
    
    public void addRaytracer(double[][] tracer){
        shapes.add(new NodeDraw(tracer[0][0], tracer[0][1], 3, Color.RED));
        for(int i=0; i<tracer.length-1; i++){
            shapes.add(new LineDraw(new Line(new Point(tracer[i][0], tracer[i][1]), new Point(tracer[i+1][0], tracer[i+1][1]))));
            shapes.add(new NodeDraw(tracer[i+1][0], tracer[i+1][1], 2));
        }
        repaint();
    }
    
    public void addShapeBase(ShapeBase shape){
        if (shape instanceof Polygon){
            for(Line l : ((Polygon) shape).lines){
                shapes.add(new LineDraw(l));
            }
        }
        else if(shape instanceof Line){
            shapes.add(new LineDraw((Line) shape));
        }
        else if(shape instanceof Circle){
            shapes.add(new CircleDraw((Circle) shape));
        }
        else if(shape instanceof CircleArc){
            shapes.add(new CircleArcDraw((CircleArc) shape));
        }
    }
    
    public void clearShapes(){
        shapes = new ArrayList<Shape>(16);
        repaint();
    }
    
}
