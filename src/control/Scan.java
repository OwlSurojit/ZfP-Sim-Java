package control;

import static control.Raytracer.*;
import drawing.DrawPanel;
import static drawing.DrawPanel.getOval2D;
import geometry.Point;
import structures.StructRayObj;
import shapesBase.Line;
import geometry.Ray;
import geometry.Vector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
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
        
        //old int pos = 0;
        int pos = 0;
        
        //old Double[][] hits = new Double[numray*(this.numref+1)][2];
        Double[][] hits = new Double[numray*(this.numref)+1][2];
        hits[0] = new Double[]{0.0, 1.0};
        
        for (int i = 0; i < numray; i++){
            // Rays gleich verteilt ueber Winkel
            Ray currentray = new Ray(this.ray.o, this.ray.r.rotate(-angle/2+(i*angle/(numray-1))));
            StructRayObj[] history = (new Raytracer(currentray, this.sender.obj, this.objects, this.numref)).trace();
            double time = 0;
            
            //old hits[pos] = new Double[]{0.0, 1.0};
            
            for (int j = 1; j < history.length; j++){
                time += (new Line(history[j].ray.o, history[j-1].ray.o)).length() / this.velocity;
                if (this.sender.get_hit(history[j].ray.o, history[j].obj)){
                    hits[pos+j][1] = this.sender.intensity / Math.pow(1.05, time);
                }else{
                    hits[pos+j][1] = 0.0;
                }
                hits[pos+j][0] = time;
            }
            
            //old pos += numref+1;
            pos += numref;
            
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
                // f(x) = 1/(x^2)  -> < 0.01 for x > 10
                double offset = scan[j][1]/(Math.pow((timedif/decaytime)*10, 2)+1);
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
    
    // Nur eine Idee
    public Double[][] processScan_A2(Double[][] scan, int numray){
        ArrayList<Double[]> pScan = new ArrayList();
        for(int i = 0; i < scan.length; i++){
            pScan.add(scan[i]);
        }
        double maxvalue = pScan.get(0)[1];
        double avgtimedif = 0;
        for (int i = 1; i < scan.length; i++){
            avgtimedif += scan[i][0] - scan[i-1][0];
        }
        avgtimedif= avgtimedif/(scan.length-1);
        int lower = 0, higher = 0;
        for (int i = 1; i < scan.length; i++){
            if (scan[i][0] - scan[i-1][0] < avgtimedif) lower ++;
            else higher++;
        }
        //avg * relativer abstand der Differenz der beiden Werte
        double areatime = avgtimedif  * (lower + higher) / 2*Math.abs(lower-higher);
        
        for (int i = 1; i < scan.length; i++){
            pScan.get(i)[1] = pScan.get(i)[1]/maxvalue;
        }
        return (Double[][])pScan.toArray();
    }
    
    // ungetestet
    public Double[][] processScan_A3(Double[][] scan){
        ArrayList<Double[]> pScan = new ArrayList();
        for(int i = 0; i < scan.length; i++){
            pScan.add(scan[i]);
        }
        
        // alle Zeitunterschiede zwischen Treffern
        Double[] timedifs = new Double[scan.length - 1];
        for (int i = 1; i < scan.length; i++){
            timedifs[i-1] = scan[i][0] - scan[i-1][0];
        }
        // Relativ gößter Sprung zwischen zwei Zeitunterschieden
        // -> kleinerer Wert = größter zwischen zusammengehörenden Treffern
        Arrays.sort(timedifs);
        int pos = 0;
        double maxdif = Double.MIN_VALUE;
        for (int i = 1; i < timedifs.length; i++){
            if (timedifs[i] != 0 && timedifs[i-1] != 0){
               double dif = timedifs[i]/timedifs[i-1];
                if (dif > maxdif){
                    maxdif = dif;
                    pos = i-1;
                } 
            }
        }
        double areatime = timedifs[pos];
        
        
        ArrayList<Double[]> hits = new ArrayList();
        while (pScan.size() > 0){
            ArrayList<Double[]> group = new ArrayList();
            group.add(pScan.get(0));
            pScan.remove(0);
            while (pScan.size() > 0 && pScan.get(0)[0] - group.get(group.size()-1)[0] < areatime){
                group.add(pScan.get(0));
                pScan.remove(0);
            }
            double avgtime = 0;
            double intensity = 0;
            for (int i = 0; i < group.size(); i++){
                avgtime += group.get(i)[0];
                intensity += group.get(i)[1];
            }
            avgtime = avgtime / group.size();
            intensity = intensity / group.size();
            hits.add(new Double[]{avgtime, intensity});
        }
        Double[][] result = new Double[hits.size()][2];
        for (int i = 0; i < hits.size(); i++){
            result[i] = hits.get(i);
        }
        return result;
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
            double length = Double.MAX_VALUE;
            if (Line.class.isInstance(object)){
                Line line = (Line) object;
                StructPointBool intersec = getLineIntersection(new Ray(this.sender.ray.o, line.toVector().toNormal()), line);
                if (intersec.bool){
                    if (intersec.point.equals(this.sender.ray.o)) length = 0;
                    // wenn dieser Fall eintritt ist der Abstand zu den Endpunkten der Linie zwingend größer
                    else length = (new Line(this.sender.ray.o, intersec.point)).length();
                }else{
                    //Abstand zu beiden Endpunkten
                    length = Math.min(this.sender.ray.o.dist(line.start), this.sender.ray.o.dist(line.end));
                }
            }
            else if (Circle.class.isInstance(object)){
                Circle circle = (Circle) object;
                double distance = this.ray.o.dist(circle.center);
                if(distance <= circle.radius){
                    length = circle.radius - distance;
                }
                else{
                    length = distance - circle.radius;
                }
            }
            else if(CircleArc.class.isInstance(object)){
                CircleArc arc = (CircleArc) object;
                // Abstand zu Kreisbogen durch Mittelpunkt
                Vector toPoint = new Vector(arc.center, this.ray.o);
                Vector toEnd = new Vector(arc.center, arc.P1());
                if(toEnd.getDirAngle(toPoint) <= arc.arcangle){
                    double distance = this.ray.o.dist(arc.center);
                    if(distance <= arc.radius){
                        length = arc.radius - distance;
                    }
                    else{
                        length = distance - arc.radius;
                    }
                }
                else{
                    length = Math.min(arc.P1().dist(this.ray.o), arc.P2().dist(this.ray.o));
                }
            }
            else if (Oval.class.isInstance(object)){
                // pass
                Oval oval1 = (Oval) object;
                Oval[] ovals = new Oval[]{new Oval(oval1.p1, oval1.p2, oval1.e + 1), new Oval(oval1.p1, oval1.p2, oval1.e - 1)};
                java.awt.Shape[] ovals2d = new java.awt.Shape[2];
                for(int i = 0; i < 2; i++){
                    Oval oval = ovals[i];
                    AffineTransform at = new AffineTransform();
                    at.setToRotation(-Math.toRadians((new Vector(1, 0)).getDirAngle((new Line(oval.p1, oval.p2).toVector()))), (oval.p1.x + oval.p2.x)/2, (oval.p1.y + oval.p2.y)/2);
                    Ellipse2D.Double oval2d = DrawPanel.getOval2D(oval);
                    ovals2d[i] = at.createTransformedShape(oval2d);
                }
                if(ovals2d[0].contains(this.ray.o.x, this.ray.o.y) && !ovals2d[1].contains(this.ray.o.x, this.ray.o.y)){
                    length = 0;
                }
            }
            if (length < mindistance){
                mindistance = length;
                closest = object;
            }
        }
        
        return objects[0];
        // TODO: nächstes Element ermitteln; prüfen, ob Abstand < 1, sonst null ausgeben.
    }
    
}
