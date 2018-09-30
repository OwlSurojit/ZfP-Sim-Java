package control;

import shapesBase.Line;
import geometry.Vector;
import geometry.Ray;
import geometry.Point;
import misc.Tools;

public class Sender {

    public double range, intensity;
    public Object obj;
    public Ray ray;

    public Sender(Ray ray, double range, Object obj, double intesity){
        this.ray = ray;
        this.range = range;
        this.obj = obj;
        this.intensity = intesity;
    }
    
    public Sender(Ray ray, double range){
        this.ray = ray;
        this.range = range;
        this.obj = null;
        this.intensity = 1;
    }
    
    // TODO: Überarbeiten, Objekt einbauen nach Implementierung von Scan.closest
    public boolean get_hit(Point point, Object obj){
        return /*this.obj.equals(obj) &&*/ (point.equals(this.ray.o) || point.dist(this.ray.o) <= this.range);
    }

    /*public Ray getRay(){
        if (Tools.equal(this.angle, 90)) return new Ray(this.pos, new Vector(0, 1));
        else if (Tools.equal(this.angle, 270)) return new Ray(this.pos, new Vector(0, -1));
        else return new Ray(this.pos, new Vector(Math.cos(Math.toRadians(this.angle)), 1*Math.sin(Math.toRadians(this.angle))));
    }*/

}
