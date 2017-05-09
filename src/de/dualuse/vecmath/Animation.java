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
	
	
}
