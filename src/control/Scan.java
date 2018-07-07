package control;

import structures.StructRayObj;
import shapesBase.Line;
import geometry.Ray;
import java.util.ArrayList;
import shapesBase.ShapeBase;

public class Scan {

    public Ray ray;
    Body body;
    public Object[] objects;
    public Sender sender;
    public int numref, delay;
    public double velocity;

    public Scan(Body body, Sender sender, int numref, double velocity, int delay){
        //this.tracer = new Raytracer(ray, sender.obj, objects, numref); //ray in Raytracer changes -> create new Raytracer for every scan
        this.ray = sender.ray;
        
        ArrayList<ShapeBase> shape_List = body.components();
        ShapeBase[] shape_Arr = new ShapeBase[shape_List.size()];
        shape_Arr = shape_List.toArray(shape_Arr);
        this.objects = shape_Arr;
        
        this.sender = sender;
        sender.obj = objects[0];
        this. numref = numref;
        this.velocity = velocity;
        this.delay = delay;
    }

    public Double[][] scan_A(){
        StructRayObj[] history = (new Raytracer(ray, this.sender.obj, this.objects, this.numref)).trace();
        double time = 0;
        Double[][] hits = new Double[numref+1][2];
        hits[0] = new Double[]{0.0,1.0};
        for (int i = 1; i < history.length; i++){
            time += (new Line(history[i].ray.o, history[i-1].ray.o)).length() / this.velocity;
            if (this.sender.get_hit(history[i].ray.o, history[i].obj)){
                hits[i][1] = this.sender.intensity / Math.pow(1.05, time);
            }else{
                hits[i][1] = 0.0;
            }
            hits[i][0] = time;
        }
        return hits;
    }

    public double[][] reflections(){
        StructRayObj[] history = (new Raytracer(ray, this.sender.obj, this.objects, this.numref)).trace();
        double[][] points = new double[history.length][2];
        for (int i = 0; i < history.length; i++){
            points[i][0] = history[i].ray.o.x;
            points[i][1] = history[i].ray.o.y;
        }
        return points;
    }
    
}
