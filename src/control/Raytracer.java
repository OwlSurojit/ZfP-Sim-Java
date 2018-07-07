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
        /*else if (Oval.class.isInstance(obj)) {
            Oval oval = (Oval)obj;
            factors = getOvalIntersection(oval);
        }*/
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

    public StructPointBool getLineIntersection(Ray ray, Line line) {
        Ray v = line.toRay();
        if (ray.r.equals(v.r)) return new StructPointBool(null, false);
        else {
            double factor = -(ray.o.x * ray.r.y - ray.o.y * ray.r.x - line.start.x * ray.r.y + line.start.y * ray.r.x) /
                    (line.start.x * ray.r.y - line.start.y * ray.r.x - line.end.x * ray.r.y + line.end.y * ray.r.x);
            return new StructPointBool(v.getPoint(factor), 0 <= factor && factor <= 1);
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
            // Winkel zwischen Start und SP
            double intersecangle = (new Vector(1,0).getDirAngle((new Line(arc.center, S)).toVector())) - arc.offsetangle;
            //Außerhalb des Kreisbogens -> kein SP mit CircleArc
            if (intersecangle > arc.arcangle) factors[0] = null;
        }
        if (factors[1] != null){
            S = this.ray.getPoint(factors[1]);
            double intersecangle = (new Vector(1,0).getDirAngle((new Line(arc.center, S)).toVector())) - arc.offsetangle;
            if (intersecangle > arc.arcangle) factors[1] = null;
        }
        return factors;
    }

    //TODO
    /*public Double[] getOvalIntersection(Oval oval){
        //copy lines for factors intelligent -> solve ** problem
    }*/

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
        if (hitCorner) return new StructRayObj(getCornerReflection(cornerObjs), cornerObjs.get(0));
        return new StructRayObj(getReflection(closestObj, getIntersection(closestObj)), closestObj);
    }

    public Ray getCornerReflection(ArrayList<Object> cornerObjs){
        double minangle = Double.POSITIVE_INFINITY;
        Line minLine = null;
        double maxangle = Double.NEGATIVE_INFINITY;
        Line maxLine = null;
        Line line = null;
        double angle;
        for (Object obj : cornerObjs){
            if (Line.class.isInstance(obj)) {
                line = (Line)obj;
            } else if (Circle.class.isInstance(obj)) {

            } else if (CircleArc.class.isInstance(obj)) {

            } else if (Oval.class.isInstance(obj)) {

            }
            angle = this.ray.r.getDirAngle(line.toVector());
            if (angle < minangle) {
                minangle = angle;
                minLine = line;
            }
            if (angle > maxangle) {
                maxangle = angle;
                maxLine = line;
            }
        }
		Vector minVec, maxVec;
		Point S;
		if (minLine.end.equals(maxLine.end)) { // both lines must start in corner
			minVec = minLine.reverse().toVector();
			maxVec = maxLine.reverse().toVector();
			S = minLine.end;
		} else if (minLine.end.equals(maxLine.start)) {
			minVec = minLine.reverse().toVector();
			maxVec = maxLine.toVector();
			S = minLine.end;
		} else if (minLine.start.equals(maxLine.end)) {
			minVec = minLine.toVector();
			maxVec = maxLine.reverse().toVector();
			S = minLine.start;
		} else {
			minVec = minLine.toVector();
			maxVec = maxLine.toVector();
			S = minLine.start;
		}
		if (Tools.equal(this.ray.r.getAngle(minVec), this.ray.r.getAngle(maxVec))) { // center
			return new Ray(S, this.ray.r.mul(-1));
		}
		if (Tools.equal(this.ray.r.getAngle(minVec) + this.ray.r.getAngle(maxVec), 360 - minVec.getAngle(maxVec))) { // inner corner, 360 because direction
			if (this.ray.r.getAngle(maxVec) < this.ray.r.getAngle(minVec)) { // minVec <- closest vector
				Vector temp = maxVec;
				maxVec = minVec;
				minVec = temp;
			}
			Vector v1 = getLineReflection(new Ray(this.ray.r.mul(-1).toPoint(), this.ray.r), minVec.toLine(), new Point(0, 0)).r;
			Vector v2 = getLineReflection(new Ray(this.ray.r.mul(-1).toPoint(), this.ray.r), maxVec.toLine(), new Point(0, 0)).r;
			if (minVec.getDirAngle(v1) > minVec.getDirAngle(maxVec)) {
				v1 = getLineReflection(new Ray(v1.mul(-1).toPoint(), v1), maxVec.toLine(), new Point(0, 0)).r;
			}
			if (maxVec.getDirAngle(v2) < maxVec.getDirAngle(minVec)) {
				v2 = getLineReflection(new Ray(v2.mul(-1).toPoint(), v2), minVec.toLine(), new Point(0, 0)).r;
			}
			return new Ray(S, v1.add(v2).mul(-1)); //TODO: normieren?
		} else { // outer corner || no corner
			if (this.ray.r.getAngle(minVec.mul(1/minVec.length()).add(maxVec.mul(1/maxVec.length()))) < 90) { // old: Tools.equal(this.ray.r.getAngle(minVec) + this.ray.r.getAngle(maxVec), minVec.getAngle(maxVec))
				Ray r = new Ray(S, minVec.mul(1/minVec.length()).add(maxVec.mul(-1/maxVec.length()))); // minVec - maxVec
				line = new Line(r.getPoint(-1), r.getPoint(1));
				return getLineReflection(this.ray, line, S);
			} else return new Ray(S, this.ray.r); // problem: counts as reflection
		}

        // Porblem: Reihenfolge der Punkte in Linie beeinflusst vektor -> addition klappt nicht
        /*Vector v = maxObj.toVector().mul(1/maxObj.length()).add(minObj.toVector().mul(1/minObj.length()));
        Point S = getLineIntersection(minObj.toRay(), maxObj).point;
        Ray r = new Ray(S, (new Vector(v.y, -v.x)));
        line = new Line(r.getPoint(-1), r.getPoint(1));*/

        // Normierung nach Laenge fehlt
        /*Point S = getLineIntersection(minObj.toRay(), maxObj).point;
        Line l1, l2;
        if (minObj.start.equals(S)) l1 = new Line(S, minObj.end);
        else l1 = new Line(S, minObj.start);
        if (maxObj.start.equals(S)) l2 = new Line(S, maxObj.end);
        else l2 = new Line(S, maxObj.start);

        Vector v = l1.toVector().mul(1/l1.length()).add(l2.toVector().mul(1/l2.length()));
        Ray r = new Ray(S, new Vector(-v.y, v.x));
        line = new Line(r.getPoint(-1), r.getPoint(1));*/


//        Point S = getLineIntersection(minObj.toRay(), maxObj).point; // alt
//        Vector v1 = minObj.toVector().mul(1/minObj.length());
//        Vector v2 = maxObj.toVector().mul(1/maxObj.length());
//        if (!minObj.start.equals(S)) v1 = v1.mul(-1);
//        if (!maxObj.start.equals(S)) v2 = v2.mul(-1);
//        Ray r = new Ray(S, v1.add(v2).toNormal());
//        line = new Line(r.getPoint(-1), r.getPoint(1));
//
//        return getLineReflection(line, S);
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
        Ray h = new Ray(ray.o, new Vector(line.end.y - line.start.y, line.start.x - line.end.x));
        Point R = getLineIntersection(h, line).point;
        Point P = (ray.o.toVector().add((new Line(ray.o, R)).toVector().mul(2))).toPoint();
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
        double factor = -(new Line(S, oval.p1)).length() / (new Line(S, oval.p2)).length();
        Vector v = (new Line(oval.p2, S)).toVector().mul(factor);
        Ray tv = new Ray(S, v.add((new Line(oval.p2, S)).toVector()));
        Line tr = new Line(tv.getPoint(-1), tv.getPoint(1));
        return getLineReflection(this.ray, tr, S);
    }

}