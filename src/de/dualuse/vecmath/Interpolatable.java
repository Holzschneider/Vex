package de.dualuse.vecmath;

public interface Interpolatable<Q extends Interpolatable<Q>> {

//	Q clone();
	Q set(Q a);
	Q line(Q a, Q b, double r);
	Q spline(Q a, Q da, Q dd, Q d, double r);
	
}
