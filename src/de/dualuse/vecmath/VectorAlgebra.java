package de.dualuse.vecmath;

interface VectorAlgebra<Q> {
	public VectorAlgebra<Q> add(Q v);
	public VectorAlgebra<Q> adds(Q v, double s);
	
	public VectorAlgebra<Q> sub(Q v);
	public VectorAlgebra<Q> scale(double s);
	
	public VectorAlgebra<Q> normalize();
	public double dot(Q v);
	
	public double norm(double p);
	
	public double length(); //euclidian length 
	public double quadrance(Q v2);
}
