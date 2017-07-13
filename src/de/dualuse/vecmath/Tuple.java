package de.dualuse.vecmath;

abstract class Tuple<T extends Tuple<T>> {

//	public abstract T fromString(String r);
	public abstract T clone();
	public abstract T self();
	
	public abstract T set(T a);
	
	final public <Q> Q to( Function<Q,T> v ) {
		return v.apply(clone());
	}

	@Override
	@SuppressWarnings("unchecked")
	final public boolean equals(Object o) {
		if (o == this)
			return true;
		else
		if (o.getClass()==this.getClass())
			return elementsEqual((T)o);
		else
			return false;
	}
	
	abstract boolean elementsEqual(T t);
}
