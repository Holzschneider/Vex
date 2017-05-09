package de.dualuse.vecmath;

import org.junit.Assert;
import org.junit.Test;

public class Matrix3dTest {
	
	@Test public void matrixInversionTest() {
		
		Matrix3d m = new Matrix3d(
					1, 2, 3, 
					2, 1, 1, 
					3, 4, 5);
		
		Matrix3d i = new Matrix3d( 
				0.5, 1, -0.5,
				-3.5, -2, 2.5,
				2.5, 1, -1.5
				);
		
//		Assert.assertEquals(i.toString(), n.toString());
		Assert.assertEquals(i,m.invert());
		
	}
	
	public static void main(String[] args) {
		new Matrix3dTest().matrixInversionTest();
	}
}


