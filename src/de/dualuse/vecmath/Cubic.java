package de.dualuse.vecmath;


public interface Cubic<Q> extends Variable<Q> {
	
	public Cubic<Q> spline(Q a, Q da, Q dd, Q d, double r);
	
}


