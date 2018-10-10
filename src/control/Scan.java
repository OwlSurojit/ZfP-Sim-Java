package control;

import static control.Raytracer.*;
import geometry.Point;
import structures.StructRayObj;
import shapesBase.Line;
import geometry.Ray;
import geometry.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import misc.Tools;
import shapesBase.Circle;
import shapesBase.CircleArc;
import shapesBase.Oval;
import shapesBase.ShapeBase;
import structures.StructPointBool;

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
        sender.obj = closest();
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
    
    // jeder Strahl wird numref mal reflektiert 
    // -> durch unterschieldiche verlaeufe werden unterschiedlice Strecken zurueckgelegt
    // -> verschiedene Zeiten im Diagramm
    // Array: {{time, intensity}, ...}
    public Double[][] MultiScan_A(int numray, double angle){
        if (numray < 2 || Tools.equal(angle, 0)) return scan_A();
        int pos = 0;
        Double[][] hits = new Double[numray*(this.numref+1)][2];
        for (int i = 0; i < numray; i++){
            // Rays gleich verteilt ueber Winkel
            Ray currentray = new Ray(this.ray.o, this.ray.r.rotate(-angle/2+(i*angle/(numray-1))));
            StructRayObj[] history = (new Raytracer(currentray, this.sender.obj, this.objects, this.numref)).trace();
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
        java.util.Arrays.sort(hits, (Double[] a, Double[] b) -> Double.compare(a[0], b[0]));
        return hits;
    }
    
    // realistischer: jeder Strahl legt die selbe Strecke zurueck
    public Double[][] MultiScan_A(int numray, double angle, double maxway){
        if (numray < 2 || Tools.equal(angle, 0)) return scan_A();
        ArrayList<Double[]> hitsAL = new ArrayList<>();
        for (int i = 0; i < numray; i++){
            // Rays gleich verteilt ueber Winkel
            Ray currentray = new Ray(this.ray.o, this.ray.r.rotate(-angle/2+(i*angle/(numray-1))));
            StructRayObj[] history = (new Raytracer(currentray, this.sender.obj, this.objects, 100)).trace();
            double time = 0.0;
            double maxtime = maxway /this.velocity;
            hitsAL.add(new Double[]{0.0, 1.0});
            int j = 1;
            while (time < maxtime){
                if(j >= history.length){
                    history = (new Raytracer(currentray, this.sender.obj, this.objects, history.length + 100)).trace();
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
    
    public Double[][] processScan_A(Double[][] scan, double decaytime){
        
        Double[][] pScan = new Double[scan.length][2];
        for(int i = 0; i < scan.length; i++){
            pScan[i] = Arrays.copyOf(scan[i], 2);
        }
        //Double[][] pScan = Arrays.copyOf(scan, scan.length);\
        //System.arraycopy(scan, 0, pScan, 0, scan.length);
        double maxvalue = pScan[0][1];
        for (int i = 1; i < scan.length; i++){
            int j = i-1;
            double timedif = scan[i][0] - scan[j][0];
            while (j >= 0 && timedif < decaytime){
                timedif = scan[i][0] - scan[j][0];
                // f(x) = 1/(x^2+1)  -> < 10^-8 for x > 3*sqrt(11111111)
                double offset = scan[j][1]/(Math.pow((timedif/decaytime), 2)*9+1);
                pScan[i][1] += offset;
                if (pScan[i][1] > maxvalue) maxvalue = pScan[i][1];
                j--;
            }
        }
        for (int i = 1; i < scan.length; i++){
            pScan[i][1] = pScan[i][1]/maxvalue;
        }
        return pScan;
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
    
    // aufgelgiedert: Strahl, Reflektion, Koordianten
    public double[][][] MultiReflections(int numray, double angle){
        if (numray < 2 || Tools.equal(angle, 0)) return new double[][][]{reflections()};
        double[][][] points = new double[numray][numref+1][2];
        for (int i = 0; i < numray; i++){
            Ray currentray = new Ray(this.ray.o, this.ray.r.rotate(-angle/2+(i*angle/(numray-1))));
            StructRayObj[] history = (new Raytracer(currentray, this.sender.obj, this.objects, this.numref)).trace();
            for(int j = 0; j < history.length; j++){
                points[i][j][0] = history[j].ray.o.x;
                points[i][j][1] = history[j].ray.o.y;
            }
        }
        return points;
    }
    
    public double[][][] MultiReflections(int numray, double angle, double maxway){
        if (numray < 2 || Tools.equal(angle, 0)) return new double[][][]{reflections()};
        double[][][] points = new double[numray][][];
        for(int i = 0; i < numray; i++){
            Ray currentray = new Ray(this.ray.o, this.ray.r.rotate(-angle/2+(i*angle/(numray-1))));
            StructRayObj[] history = (new Raytracer(currentray, this.sender.obj, this.objects, 100)).trace();
            ArrayList<double[]> pointsAL = new ArrayList<>();
            double way = -(new Line(history[0].ray.o, history[1].ray.o)).length();
            int j = 0;
            while (way < maxway){
                if (j >= history.length - 1){
                    history = (new Raytracer(currentray, this.sender.obj, this.objects, history.length + 100)).trace();
                }
                pointsAL.add(new double[]{history[j].ray.o.x, history[j].ray.o.y});
                way += (new Line(history[j].ray.o, history[j+1].ray.o)).length();
                j++;
            }
            points[i] = new double[pointsAL.size()][2];
            for(int k = 0; k < pointsAL.size(); k++){
                points[i][k] = pointsAL.get(k);
            }
        }
        return points;
    }

    private Object closest() {
        
        double mindistance = 1;
        Object closest = null;
        for(Object object : this.objects){
            if (Line.class.isInstance(object)){
                Line line = (Line) object;
                StructPointBool intersec = getLineIntersection(new Ray(this.sender.ray.o, line.toVector().toNormal()), line);
                if (intersec.bool){
                    // wenn dieser Fall eintritt ist der Abstand zu den Endpznkten der Linie zwingend größer
                    double length = (new Line(this.sender.ray.o, intersec.point)).length();
                    if (length < mindistance){
                        mindistance = length;
                        closest = line;
                    }
                }else{
                    //Abstand zu beiden Endpunkten
                    double length = Math.min((new Line(this.sender.ray.o, line.start)).length(), (new Line(this.sender.ray.o, line.end)).length());
                    if (length < mindistance){
                        mindistance = length;
                        closest = line;
                    }
                }
            }
            else if (Circle.class.isInstance(object)){
                Circle circle = (Circle) object;
                if (this.sender.ray.o.equals(circle.center)) return circle.radius;
                Ray r = new Ray(this.sender.ray.o, (new Line(this.sender.ray.o, circle.center)).toVector());
                Double[] factors = getCircleIntersection(r, circle);
                double length = (new Line(this.sender.ray.o, r.getPoint(Math.min(factors[0], factors[1])))).length();
                if (length < mindistance){
                    mindistance = length;
                    closest = circle;
                }
            }
            else if(CircleArc.class.isInstance(object)){
                CircleArc arc = (CircleArc) object;
                // Abstand zu Kreisbogen durch Mittelpunkt
                Ray r = new Ray(this.sender.ray.o, (new Line(this.sender.ray.o, arc.center)).toVector());
                Double[] factors = getCircleArcIntersection(r, arc);
                double len1; 
                if (factors[0] != null && factors[1] != null) len1 = (new Line(this.sender.ray.o, r.getPoint(Math.min(factors[0], factors[1])))).length();
                else if (factors[0] != null) len1 = (new Line(this.sender.ray.o, r.getPoint(factors[0]))).length();
                else if (factors[1] != null) len1 = (new Line(this.sender.ray.o, r.getPoint(factors[1]))).length();
                else len1 = Double.MAX_VALUE;
                //Abstand zu Endpunkten
                double len2 = (new Line(this.sender.ray.o, arc.P1())).length();
                double len3 = (new Line(this.sender.ray.o, arc.P2())).length();
                double length = Math.min(len1, len2);
                length = Math.min(length, len3);
                if (length < mindistance){
                    mindistance = length;
                    closest = arc;
                }
            }
            else if (Oval.class.isInstance(object)){
                
            }
        }
        
        return objects[0];
        // TODO: nächstes Element ermitteln; prüfen, ob Abstand < 1, sonst null ausgeben.
    }
    
}
