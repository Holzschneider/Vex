package de.dualuse.vecmath;

public interface Interpolatable<Q> {

	Interpolatable<Q> point(Q a);
	Interpolatable<Q> line(Q a, Q b, double r);
	Interpolatable<Q> spline(Q a, Q da, Q dd, Q d, double r);
	
}
