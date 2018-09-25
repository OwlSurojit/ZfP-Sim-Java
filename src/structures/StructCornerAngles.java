/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import geometry.Vector;

/**
 *
 * @author morit
 */
public class StructCornerAngles {
    
    public double minangle, maxangle;
    public Vector minVec, maxVec;
    
    public StructCornerAngles(double minangle, double maxangle, Vector minVec, Vector maxVec){
        this.minangle = minangle;
        this.maxangle = maxangle;
        this.minVec = minVec;
        this.maxVec = maxVec;
    }
}
