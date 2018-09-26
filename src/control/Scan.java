package control;

import structures.StructRayObj;
import shapesBase.Line;
import geometry.Ray;
import java.util.ArrayList;
import misc.Tools;
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
        StructRayObj[] history = (new Raytracer(this.ray, this.sender.obj, this.objects, this.numref)).trace();
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
        StructRayObj[] history = (new Raytracer(this.ray, this.sender.obj, this.objects, this.numref)).trace();
        double[][] points = new double[history.length][2];
        for (int i = 0; i < history.length; i++){
            points[i][0] = history[i].ray.o.x;
            points[i][1] = history[i].ray.o.y;
        }
        return points;
    }
    
    // jeder Strahl wird numref mal reflektiert 
    // -> durch unterschieldiche verlaeufe werden unterschiedlice Strecken zurueckgelegt
    // -> verschiedene Zeiten im Diagramm
    public Double[][] MultiScan_A(int numray, double angle){
        if (numray < 2 || Tools.equal(angle, 0)) return scan_A();
        int pos = 0;
        Double[][] hits = new Double[numray*(this.numref+1)][2];
        for (int i = 0; i < numray; i++){
            // Rays gleich verteilt ueber Winkel
            Ray ray = new Ray(this.ray.o, this.ray.r.rotate(-angle/2+(i*angle/(numray-1))));
            StructRayObj[] history = (new Raytracer(ray, this.sender.obj, this.objects, this.numref)).trace();
            double time = 0;
            hits[pos] = new Double[]{0.0, 1.0};
            for (int j = 1; j < history.length; j++){
                time += (new Line(history[j].ray.o, history[j-1].ray.o)).length() / this.velocity;
                if (this.sender.get_hit(history[j].ray.o, history[j].obj)){
                    hits[pos+j][1] = this.sender.intensity / Math.pow(1.05, time);
                }else{
                    hits[pos+j][1] = 0.0;
                }
                hits[pos+j][0] = time;
            }
            pos += numref+1;
        }
        java.util.Arrays.sort(hits, new java.util.Comparator<Double[]>() {
            public int compare(Double[] a, Double[] b) {
                return Double.compare(a[0], b[0]);
            }
        });
        return hits;
    }
    
    // realistischer: jeder Strahl legt die selbe Strecke zurueck
    public Double[][] MultiScan_A(int numray, double angle, double maxway){
        if (numray < 2 || Tools.equal(angle, 0)) return scan_A();
        this.numref = 100;
        ArrayList<Double[]> hitsAL = new ArrayList<Double[]>();
        for (int i = 0; i < numray; i++){
            // Rays gleich verteilt ueber Winkel
            Ray ray = new Ray(this.ray.o, this.ray.r.rotate(-angle/2+(i*angle/(numray-1))));
            StructRayObj[] history = (new Raytracer(ray, this.sender.obj, this.objects, this.numref)).trace();
            double time = 0.0;
            double maxtime = maxway /this.velocity;
            hitsAL.add(new Double[]{0.0, 1.0});
            int j = 1;
            while (time < maxtime){
                if(j >= history.length){
                    history = (new Raytracer(ray, this.sender.obj, this.objects, history.length + this.numref)).trace();
                }
                time += (new Line(history[j].ray.o, history[j-1].ray.o)).length() / this.velocity;
                double cint; 
                if (this.sender.get_hit(history[j].ray.o, history[j].obj)){
                    cint = this.sender.intensity / Math.pow(1.05, time);
                }else{
                    cint = 0.0;
                }
                hitsAL.add(new Double[]{time, cint});
                j++;
            } 
        }
        Double[][] hits = new Double[hitsAL.size()][2];
        for (int i = 0; i < hitsAL.size(); i++){
            hits[i] = hitsAL.get(i);
        }
        java.util.Arrays.sort(hits, (Double[] a, Double[] b) -> Double.compare(a[0], b[0]));
        return hits;
    }
}
