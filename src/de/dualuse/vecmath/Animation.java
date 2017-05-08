package de.dualuse.vecmath;

public interface Animation<T> {
	
	/**
	 * Writes the calculated value of the animation at time t into v
	 * @param <Q>  sub class of T
	 * @param t  time of animation
	 * @param v  object of type Q that shall be filled with value
	 * @return  the v object
	 */
	public<Q extends T> Q get(double t, Q v);
	
//	/**
//	 * Writes the calculated animation values at the time points t and t+dt into a and b 
//	 * @param t  the given time of animation
//	 * @param dt  the given time difference between two frames 
//	 * @param a  value of time t to be filled
//	 * @param b  value of time t+dt to be filled
//	 * @return the current animation bounds
//	 */
//	public<Q extends T, R extends T> void get(double t, double dt, Q a, R b);
	
}
