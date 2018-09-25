package control;

import shapesBase.ShapeBase;
import geometry.Ray;
import shapesBase.Line;
import shapesBase.CircleArc;
import geometry.Vector;
import shapesBase.Circle;
import shapesBase.Oval;
import geometry.Point;
import structures.StructPointBool;
import structures.StructRayObj;
import java.util.ArrayList;
import misc.Tools;

public class Raytracer {

    public Ray ray;
    public Object startobj;
    public Object[] objects;
    public StructRayObj[] history;
    public int num_ref;

    public Raytracer(Ray ray, Object startobj, Body body, int num_ref) {
        this.ray = ray;
        this.startobj = startobj;

        ArrayList<ShapeBase> shape_List = body.components();
        ShapeBase[] shape_Arr = new ShapeBase[shape_List.size()];
        shape_Arr = shape_List.toArray(shape_Arr);
        this.objects = shape_Arr;

        this.num_ref = num_ref;
        this.history = new StructRayObj[num_ref + 1];
    }

    public Raytracer(Ray ray, Object startobj, Object[] objects, int num_ref) {
        this.ray = ray;
        this.startobj = startobj;
        this.objects = objects;
        this.num_ref = num_ref;
        this.history = new StructRayObj[num_ref + 1];
    }

    public StructRayObj[] trace() { //TODO
        StructRayObj reflection = new StructRayObj(this.ray, this.startobj);
        this.history[0] = reflection;
        for (int i = 0; i < this.num_ref; i++) {
            reflection = getClosestReflection();
            this.history[i+1] = reflection;
            this.ray = reflection.ray;
        }
        return this.history;
    }

    public Point getIntersection(Object obj) {
        Double[] factors;
        if (Line.class.isInstance(obj)) {
            Line line = (Line)obj;
            StructPointBool data = getLineIntersection(this.ray, line);
            if (data.bool) return data.point;
            else return null;
        }
        else if (Circle.class.isInstance(obj)) {
            Circle circle = (Circle)obj;
            factors = getCircleIntersection(circle);
        }
        else if (CircleArc.class.isInstance(obj)) {
            CircleArc arc = (CircleArc)obj;
            factors = getCircleArcIntersection(arc);
        }
        else if (Oval.class.isInstance(obj)) {
            Oval oval = (Oval)obj;
            factors = getOvalIntersection(oval);
        }
        else {
            System.err.println("Can't get intersection: obj has an unsupported type");
            System.exit(1);
            return null;
        }

        Point S;
        if (factors[0] != null && factors[1] != null) S = this.ray.getPoint(Math.min(factors[0], factors[1]));
        else if (factors[0] != null) S = this.ray.getPoint(factors[0]);
        else if (factors[1] != null) S = this.ray.getPoint(factors[1]);
        else return null;
        return S;
    }

    public static StructPointBool getLineIntersection(Ray ray, Line line) {
        Ray v = line.toRay();
        if (ray.r.equals(v.r)) return new StructPointBool(null, false);
        else {
            double factor = -(ray.o.x * ray.r.y - ray.o.y * ray.r.x - line.start.x * ray.r.y + line.start.y * ray.r.x) /
                    (line.start.x * ray.r.y - line.start.y * ray.r.x - line.end.x * ray.r.y + line.end.y * ray.r.x);
            return new StructPointBool(v.getPoint(factor), 0 <= factor && factor <= 1 || Tools.equal(0, factor) || Tools.equal(1, factor));
        }
    }

    public Double[] getCircleIntersection(Circle circle){
        double p = 2 * (this.ray.r.x * (this.ray.o.x - circle.center.x) + this.ray.r.y * (this.ray.o.y - circle.center.y)) /
                (Math.pow(this.ray.r.x, 2) + Math.pow(this.ray.r.y, 2));
        double q = (Math.pow(this.ray.o.x, 2) + Math.pow(this.ray.o.y, 2) + Math.pow(circle.center.x, 2) + Math.pow(circle.center.y, 2) -
                2 * (this.ray.o.x * circle.center.x + this.ray.o.y * circle.center.y) - Math.pow(circle.radius, 2)) /
                (Math.pow(this.ray.r.x, 2) + Math.pow(this.ray.r.y, 2));
        if (Math.pow(p/2, 2)-q <= 0){
            return new Double[]{null, null};
        }else{
            Double factor1 = -p/2 + Math.sqrt(Math.pow(p/2, 2)-q);
            Double factor2 = -p/2 - Math.sqrt(Math.pow(p/2, 2)-q);
            if (factor1 < 0 || Tools.equal(factor1, 0)) factor1 = null;
            if (factor2 < 0 || Tools.equal(factor2, 0)) factor2 = null;
            return new Double[]{factor1, factor2};
        }
    }

    public Double[] getCircleArcIntersection(CircleArc arc){
        Double[] factors = getCircleIntersection(arc.toCircle());
        Point S;
        if (factors[0] != null) {
            S = this.ray.getPoint(factors[0]);
            Vector S_offset = new Vector(Math.cos(Math.toRadians(arc.offsetangle)), -Math.sin(Math.toRadians(arc.offsetangle)));
            if (S_offset.getDirAngle((new Line(arc.center, S)).toVector()) > arc.arcangle) factors[0] = null;
//            // Winkel zwischen Start und SP
//            intersecangle = (new Vector(1,0).getDirAngle((new Line(arc.center, S)).toVector())) - arc.offsetangle;
//            //Außerhalb des Kreisbogens -> kein SP mit CircleArc
//            if (intersecangle < 0 || intersecangle > arc.arcangle) factors[0] = null;
        }
        if (factors[1] != null){
            S = this.ray.getPoint(factors[1]);
            Vector S_offset = new Vector(Math.cos(Math.toRadians(arc.offsetangle)), -Math.sin(Math.toRadians(arc.offsetangle)));
            if (S_offset.getDirAngle((new Line(arc.center, S)).toVector()) > arc.arcangle) factors[1] = null;
//            intersecangle = (new Vector(1,0).getDirAngle((new Line(arc.center, S)).toVector())) - arc.offsetangle;
//            if (intersecangle < 0 || intersecangle > arc.arcangle) factors[1] = null;
        }
        return factors;
    }

    //TODO
    public Double[] getOvalIntersection(Oval oval){
        Double factor1 = ((-(oval.e*Math.sqrt(Math.pow(oval.e, 4)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*Math.pow(oval.e, 2)*(2*Math.pow(this.ray.o.x, 2)*Math.pow(this.ray.r.y, 2)-2*this.ray.o.x*(2*this.ray.o.y*this.ray.r.x+oval.p1.x*this.ray.r.y-oval.p1.y*this.ray.r.x+oval.p2.x*this.ray.r.y-oval.p2.y*this.ray.r.x)*this.ray.r.y+2*Math.pow(this.ray.o.y, 2)*Math.pow(this.ray.r.x, 2)+2*this.ray.o.y*(oval.p1.x*this.ray.r.y-oval.p1.y*this.ray.r.x+oval.p2.x*this.ray.r.y-oval.p2.y*this.ray.r.x)*this.ray.r.x+Math.pow(oval.p1.x, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*oval.p1.x*(oval.p2.x*this.ray.r.x+oval.p2.y*this.ray.r.y)*this.ray.r.x+Math.pow(oval.p1.y, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*oval.p1.y*(oval.p2.x*this.ray.r.x+oval.p2.y*this.ray.r.y)*this.ray.r.y+(Math.pow(oval.p2.x, 2)+Math.pow(oval.p2.y, 2))*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2)))+(4*Math.pow(this.ray.o.x, 2)*Math.pow(this.ray.r.y, 2)-4*this.ray.o.x*(2*this.ray.o.y*this.ray.r.x+oval.p1.x*this.ray.r.y-oval.p1.y*this.ray.r.x+oval.p2.x*this.ray.r.y-oval.p2.y*this.ray.r.x)*this.ray.r.y+4*Math.pow(this.ray.o.y, 2)*Math.pow(this.ray.r.x, 2)+4*this.ray.o.y*(oval.p1.x*this.ray.r.y-oval.p1.y*this.ray.r.x+oval.p2.x*this.ray.r.y-oval.p2.y*this.ray.r.x)*this.ray.r.x+Math.pow(oval.p1.x, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*oval.p1.x*(oval.p2.x*(Math.pow(this.ray.r.x, 2)-Math.pow(this.ray.r.y, 2))+2*oval.p2.y*this.ray.r.x*this.ray.r.y)+Math.pow(oval.p1.y, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*oval.p1.y*(2*oval.p2.x*this.ray.r.x*this.ray.r.y-oval.p2.y*(Math.pow(this.ray.r.x, 2)-Math.pow(this.ray.r.y, 2)))+(Math.pow(oval.p2.x, 2)+Math.pow(oval.p2.y, 2))*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2)))*(Math.pow(oval.p1.x, 2)-2*oval.p1.x*oval.p2.x+Math.pow(oval.p1.y, 2)-2*oval.p1.y*oval.p2.y+Math.pow(oval.p2.x, 2)+Math.pow(oval.p2.y, 2)))+Math.pow(oval.e, 2)*(2*this.ray.o.x*this.ray.r.x+2*this.ray.o.y*this.ray.r.y-oval.p1.x*this.ray.r.x-oval.p1.y*this.ray.r.y-oval.p2.x*this.ray.r.x-oval.p2.y*this.ray.r.y)-(2*this.ray.o.x*(oval.p1.x-oval.p2.x)+2*this.ray.o.y*(oval.p1.y-oval.p2.y)-Math.pow(oval.p1.x, 2)-Math.pow(oval.p1.y, 2)+Math.pow(oval.p2.x, 2)+Math.pow(oval.p2.y, 2))*(oval.p1.x*this.ray.r.x+oval.p1.y*this.ray.r.y-oval.p2.x*this.ray.r.x-oval.p2.y*this.ray.r.y)))/(2*(Math.pow(oval.e, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-Math.pow(oval.p1.x*this.ray.r.x+oval.p1.y*this.ray.r.y-oval.p2.x*this.ray.r.x-oval.p2.y*this.ray.r.y, 2))));
        Double factor2 = ((oval.e*Math.sqrt(Math.pow(oval.e, 4)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*Math.pow(oval.e, 2)*(2*Math.pow(this.ray.o.x, 2)*Math.pow(this.ray.r.y, 2)-2*this.ray.o.x*(2*this.ray.o.y*this.ray.r.x+oval.p1.x*this.ray.r.y-oval.p1.y*this.ray.r.x+oval.p2.x*this.ray.r.y-oval.p2.y*this.ray.r.x)*this.ray.r.y+2*Math.pow(this.ray.o.y, 2)*Math.pow(this.ray.r.x, 2)+2*this.ray.o.y*(oval.p1.x*this.ray.r.y-oval.p1.y*this.ray.r.x+oval.p2.x*this.ray.r.y-oval.p2.y*this.ray.r.x)*this.ray.r.x+Math.pow(oval.p1.x, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*oval.p1.x*(oval.p2.x*this.ray.r.x+oval.p2.y*this.ray.r.y)*this.ray.r.x+Math.pow(oval.p1.y, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*oval.p1.y*(oval.p2.x*this.ray.r.x+oval.p2.y*this.ray.r.y)*this.ray.r.y+(Math.pow(oval.p2.x, 2)+Math.pow(oval.p2.y, 2))*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2)))+(4*Math.pow(this.ray.o.x, 2)*Math.pow(this.ray.r.y, 2)-4*this.ray.o.x*(2*this.ray.o.y*this.ray.r.x+oval.p1.x*this.ray.r.y-oval.p1.y*this.ray.r.x+oval.p2.x*this.ray.r.y-oval.p2.y*this.ray.r.x)*this.ray.r.y+4*Math.pow(this.ray.o.y, 2)*Math.pow(this.ray.r.x, 2)+4*this.ray.o.y*(oval.p1.x*this.ray.r.y-oval.p1.y*this.ray.r.x+oval.p2.x*this.ray.r.y-oval.p2.y*this.ray.r.x)*this.ray.r.x+Math.pow(oval.p1.x, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*oval.p1.x*(oval.p2.x*(Math.pow(this.ray.r.x, 2)-Math.pow(this.ray.r.y, 2))+2*oval.p2.y*this.ray.r.x*this.ray.r.y)+Math.pow(oval.p1.y, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-2*oval.p1.y*(2*oval.p2.x*this.ray.r.x*this.ray.r.y-oval.p2.y*(Math.pow(this.ray.r.x, 2)-Math.pow(this.ray.r.y, 2)))+(Math.pow(oval.p2.x, 2)+Math.pow(oval.p2.y, 2))*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2)))*(Math.pow(oval.p1.x, 2)-2*oval.p1.x*oval.p2.x+Math.pow(oval.p1.y, 2)-2*oval.p1.y*oval.p2.y+Math.pow(oval.p2.x, 2)+Math.pow(oval.p2.y, 2)))-Math.pow(oval.e, 2)*(2*this.ray.o.x*this.ray.r.x+2*this.ray.o.y*this.ray.r.y-oval.p1.x*this.ray.r.x-oval.p1.y*this.ray.r.y-oval.p2.x*this.ray.r.x-oval.p2.y*this.ray.r.y)+(2*this.ray.o.x*(oval.p1.x-oval.p2.x)+2*this.ray.o.y*(oval.p1.y-oval.p2.y)-Math.pow(oval.p1.x, 2)-Math.pow(oval.p1.y, 2)+Math.pow(oval.p2.x, 2)+Math.pow(oval.p2.y, 2))*(oval.p1.x*this.ray.r.x+oval.p1.y*this.ray.r.y-oval.p2.x*this.ray.r.x-oval.p2.y*this.ray.r.y))/(2*(Math.pow(oval.e, 2)*(Math.pow(this.ray.r.x, 2)+Math.pow(this.ray.r.y, 2))-Math.pow(oval.p1.x*this.ray.r.x+oval.p1.y*this.ray.r.y-oval.p2.x*this.ray.r.x-oval.p2.y*this.ray.r.y, 2))));
        if(factor1.isNaN()) factor1 = null;
        if(factor2.isNaN()) factor2 = null;
        return new Double[]{factor1, factor2};
    }

    public StructRayObj getClosestReflection() {
        Point S;
        double mindistance = Double.POSITIVE_INFINITY;
        Object closestObj = null;
        ArrayList<Object> cornerObjs = new ArrayList<Object>();
        boolean hitCorner = false;
        for (Object obj : this.objects) {
            S = getIntersection(obj);
            if (S != null && !S.equals(this.ray.o)) {
                Vector direction = (new Line(this.ray.o, S)).toVector();
                double distance = direction.length();
                if (this.ray.r.x * direction.x >= 0 && this.ray.r.y * direction.y >= 0 && Tools.equal(distance, mindistance)){
                    if (cornerObjs.isEmpty()) cornerObjs.add(closestObj);
                    cornerObjs.add(obj);
                    hitCorner = true;
                } else if (this.ray.r.x * direction.x >= 0 && this.ray.r.y * direction.y >= 0 && distance < mindistance) { //TODO: hit in corner
                    mindistance = distance;
                    closestObj = obj;
                    hitCorner = false;
                    cornerObjs.clear();
                }
            }
        }
        if (hitCorner) return new StructRayObj(getCornerReflection(cornerObjs, getIntersection(cornerObjs.get(0))), cornerObjs.get(0));
	else return new StructRayObj(getReflection(closestObj, getIntersection(closestObj)), closestObj);
    }

    public Ray getCornerReflection(ArrayList<Object> cornerObjs, Point S){
        double minangle = Double.POSITIVE_INFINITY;
        Vector maxVec = null;
        double maxangle = Double.NEGATIVE_INFINITY;
        Vector minVec = null;
        Line line = null;
        double angle;
        // search lines for reflection
        for (Object obj : cornerObjs){
            // Line, Reflection happens on
            if (Line.class.isInstance(obj)) {
                line = (Line)obj;
            } else if (Circle.class.isInstance(obj)) {

            } else if (CircleArc.class.isInstance(obj)) {

            } else if (Oval.class.isInstance(obj)) {

            }
            
            Vector vector;
            if (S.equals(line.start)) vector = line.toVector();
            else vector = line.toVector().mul(-1);

            // two lines closest to Ray
            angle = this.ray.r.getDirAngle(vector);
            if (angle < minangle) {
                minangle = angle;
                minVec = vector;
            }
            if (angle > maxangle) {
                maxangle = angle;
                maxVec = vector;
            }
        }
        
        // Ray in center between Lines -> return inverted
        if (Tools.equal(this.ray.r.getAngle(minVec), this.ray.r.getAngle(maxVec))) {
                return new Ray(S, this.ray.r.mul(-1));
        }
        // inner corner, 360 because different direction
        if (Tools.equal(this.ray.r.getAngle(minVec) + this.ray.r.getAngle(maxVec), 360 - minVec.getAngle(maxVec))) {

                Ray RayVr = new Ray(this.ray.r.mul(-1).toPoint(), this.ray.r);
                // Reflection at Point (0|0) -> Ray to this Point + Line from there
                Vector v1 = getLineReflection(RayVr, minVec.toLine(), new Point(0, 0)).r;
                // v1 hits Vec2 -> 2nd Reflection
                if (minVec.getDirAngle(v1) > minVec.getDirAngle(maxVec)){
                    v1 = getLineReflection(new Ray(v1.mul(-1).toPoint(), v1), maxVec.toLine(), new Point(0,0)).r;
                }
                Vector v2 = getLineReflection(RayVr, maxVec.toLine(), new Point(0, 0)).r;
                // v2 hits Vec1 -> 2nd Reflection
                if(maxVec.getDirAngle(v2) <  maxVec.getDirAngle(minVec)){
                    v2 = getLineReflection(new Ray(v2.mul(-1).toPoint(), v2), minVec.toLine(), new Point(0,0)).r;
                }
                return new Ray(S, v1.add(v2));
        // ray passes at outer corner
        } else if(this.ray.r.getDirAngle(minVec) > 180 && this.ray.r.getDirAngle(maxVec) > 180 || this.ray.r.getDirAngle(minVec) < 180 && this.ray.r.getDirAngle(maxVec) < 180){
            return new Ray(S, this.ray.r);
        // outer corner reflection
        } else {
            Ray normal = new Ray(S, minVec.mul(1/minVec.length()).add(maxVec.mul(1/maxVec.length())).toNormal());
            Line l = new Line(normal.getPoint(-1), normal.getPoint(1));
            return getLineReflection(this.ray, l, S);
        }
    }

    public Ray getReflection(Object obj, Point S){
        if(Line.class.isInstance(obj)){
            Line line = (Line)obj;
            return getLineReflection(this.ray, line, S);
        }else if(Circle.class.isInstance(obj)){
            Circle circle = (Circle)obj;
            return getCircleReflection(circle, S);
        }else if(CircleArc.class.isInstance(obj)){
            Circle circle = ((CircleArc)obj).toCircle();
            return getCircleReflection(circle, S);
        }else if(Oval.class.isInstance(obj)){
            Oval oval = (Oval)obj;
            return getOvalReflection(oval, S);
        }else{
            System.err.println("Can't get reflection: obj has an unsupported type");
            System.exit(1);
            return null;
        }
    }

    public Ray getLineReflection(Ray ray, Line line, Point S){
        // vertical to line
        Ray h = new Ray(ray.o, new Vector(line.end.y - line.start.y, line.start.x - line.end.x));
        // dropped perpendicular foot (Lotfußpunkt) of ray.o on the line
        Point R = getLineIntersection(h, line).point;
        // ray.o mirrored on the line
        Point P = (ray.o.toVector().add((new Line(ray.o, R)).toVector().mul(2))).toPoint();
        // intersection ray, line and Vector P -> S
        return new Ray(S, new Vector(S.x - P.x, S.y - P.y));
    }

    public Ray getCircleReflection(Circle circle, Point S){
        Vector r1 = (new Line(circle.center, S)).toVector();
        Ray r2 = new Ray(S, new Vector(-r1.y, r1.x)); //senkrecht zu r1
        Line line = new Line(r2.getPoint(-1), r2.getPoint(1)); // Linie, an der reflektiert wird
        if (line.toVector().equals(this.ray.r))return this.ray;
        return getLineReflection(this.ray, line, S);
    }

    public Ray getOvalReflection(Oval oval, Point S){
        /*double factor = -(new Line(S, oval.p1)).length() / (new Line(S, oval.p2)).length();
        Vector v = (new Line(oval.p2, S)).toVector().mul(factor);
        Ray tv = new Ray(S, v.add((new Line(oval.p2, S)).toVector()));
        Line tr = new Line(tv.getPoint(-1), tv.getPoint(1));
        return getLineReflection(this.ray, tr, S);*/
        
        // Weg ueber Kreis um Oval:
        // Schnittpunkt des Strahls p1 ueber S auf Kreis k = T
        Ray rk = new Ray(oval.p1, (new Line(oval.p1, S)).toVector());
        Point T = rk.getPoint(oval.e/(new Line(oval.p1, S).length()));
        // Vector der Tangente als Summe beider (gleic langer) Teilvekoren
        Vector vt = (new Line(oval.p2, S)).toVector().add((new Line(T, S)).toVector());
        Ray rt = new Ray(S, vt);
        // Tagnente aus 2 Punkten im Strahl an S
        Line t = new Line(rt.getPoint(-1), rt.getPoint(1));
        
        
        // Weg ueber Brennpunkteigenschaft (Winkelhalbierende = Normale)
        // Winkel der Normalen zu Bezug (1,0)
        double globalNormAngle = Math.toRadians((new Vector(1,0)).getDirAngle((new Line(S, oval.p1)).toVector()) + (new Line(S, oval.p1)).toVector().getAngle((new Line(S, oval.p2).toVector()))/2);
        Vector vt1 = (new Vector(Math.sin(globalNormAngle), Math.cos(globalNormAngle))).toNormal();
        Ray rt1 = new Ray(S, vt1);
        Line t1 = new Line(rt1.getPoint(-1), rt1.getPoint(1));
        
        
        return getLineReflection(this.ray, t, S);
    }

}