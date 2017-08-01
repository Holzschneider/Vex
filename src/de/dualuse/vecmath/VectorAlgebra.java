package de.dualuse.vecmath;

interface VectorAlgebra<Q> {
	public VectorAlgebra<Q> add(Q v);
	public VectorAlgebra<Q> adds(Q v, double s);
	
	public VectorAlgebra<Q> sub(Q v);
	public VectorAlgebra<Q> scale(double s);
	
	public VectorAlgebra<Q> mul(Q v); 
	public VectorAlgebra<Q> div(Q v); 
	
	public VectorAlgebra<Q> sum(Q a, Q b);
	public VectorAlgebra<Q> difference(Q a, Q b);
	
	public VectorAlgebra<Q> normalize();
	public double dot(Q v);
	
	public double norm(double p);
	
	public double length(); //euclidean length 
	public double distance(Q v2); //euclidean distance
	public double quadrance(Q v2);
}
