package de.dualuse.vecmath;

abstract class Vector<VectorType extends Vector<VectorType>> 
extends Tuple<VectorType>
implements VectorAlgebra<VectorType>, Interpolatable<VectorType>
{
	
}
