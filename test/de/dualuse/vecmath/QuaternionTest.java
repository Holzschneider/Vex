package de.dualuse.vecmath;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QuaternionTest {

	@Test
	public void testNormalization() {
		Quaternion q1 = new Quaternion(3, 4, 3, -12);
		// https://www.wolframalpha.com/input/?i=normalize+quaternion+3i%2B4j%2B3k-12
		Quaternion normalizedShouldBe = new Quaternion(3d / sqrt(178d), 2d * sqrt(2d / 89d), 3d / sqrt(178d), -6d * sqrt(2d / 89d));
		assertEquals(normalizedShouldBe, q1.normalize());

		Quaternion q2 = new Quaternion(-1, 3, -3, 4);
		// https://www.wolframalpha.com/input/?i=normalize+quaternion+-1i%2B3j-3k%2B4
		normalizedShouldBe = new Quaternion(-1d / sqrt(35d), 3d / sqrt(35d), -3d / sqrt(35d), 4d / sqrt(35d));
		assertEquals(normalizedShouldBe, q2.normalize());
	}

	@Test
	public void testSetConcatenation() {
		Quaternion q1 = new Quaternion(3, 4, 3, -12);
		Quaternion q2 = new Quaternion(-1, 3, -3, 4);
		Quaternion product = new Quaternion().setConcatenation(q1, q2);
		// https://www.wolframalpha.com/input/?i=quaternion+3i%2B4j%2B3k-12+multiplied+by+-1i%2B3j-3k%2B4
		Quaternion productShoudBe = new Quaternion(3, -14, 61, -48);
		assertEquals(productShoudBe.normalize(), product.normalize());
	}

	@Test
	public void testConcatenate() {
		Quaternion q1 = new Quaternion(3, 4, 3, -12);
		Quaternion q2 = new Quaternion(-1, 3, -3, 4);
		q1.concatenate(q2);
		// https://www.wolframalpha.com/input/?i=quaternion+3i%2B4j%2B3k-12+multiplied+by+-1i%2B3j-3k%2B4
		Quaternion productShoudBe = new Quaternion(3, -14, 61, -48);
		assertEquals(productShoudBe.normalize(), q1.normalize());
	}

}
