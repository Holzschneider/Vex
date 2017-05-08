package de.dualuse.vecmath;

public interface Linear<Q> extends Variable<Q> {
	
	Linear<Q> line(Q a, Q b, double r);
	
}
