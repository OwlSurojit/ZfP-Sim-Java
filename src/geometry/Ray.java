package geometry;

public class Ray {
    public Point o;
    public Vector r;
    
    public Ray(Point o, Vector r){
        this.o = o;
        this.r = r;
    }
    
    public Point getPoint(double factor){
        return new Point(o.x + factor*r.x, o.y + factor*r.y);
    }
    
    @Override
    public String toString(){
        return "Ray(o = " + o + ", r = " + r + ")";
    }
}
