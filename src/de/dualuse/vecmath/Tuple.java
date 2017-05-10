package de.dualuse.vecmath;

abstract class Tuple<T> implements Value<T,T> {

	public abstract T fromString(String r);
	public abstract T clone();
	
	public abstract T set(T a);
	
	@SuppressWarnings("unchecked")
	final public <Q> Q to( Value<Q,T> v ) {
		return v.set((T)this);
	}
	
	final public <Q> Q get( Value<Q,T> v ) {
		return to(v);
	}
	
	@Override
	final public boolean equals(Object o) {
		if (o == this)
			return true;
		else
//		if (o.getClass()==this.getClass())
			return elementsEqual((T)o);
//		else
//			return false;
	}
	
	abstract boolean elementsEqual(T t);
}
