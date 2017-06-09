package de.dualuse.vecmath;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Test;

public class ScalarTest {
	@Test public void parsing() {
		Pattern DECIMAL = Scalar.DECIMAL;
		assertFalse(DECIMAL.matcher("2h").matches() ) ;
		assertTrue( DECIMAL.matcher(".0").matches() ) ;
		assertTrue( DECIMAL.matcher("20").matches() ) ;
		assertTrue( DECIMAL.matcher("20.").matches() ) ;
		assertTrue( DECIMAL.matcher("20.1").matches() ) ;
		assertFalse(DECIMAL.matcher("").matches() ) ;
		assertTrue( DECIMAL.matcher("123").matches() ) ;

		assertFalse(DECIMAL.matcher("123e").matches() ) ;
		assertTrue( DECIMAL.matcher("123e12").matches() ) ;
		assertTrue( DECIMAL.matcher(".234e12").matches() ) ;
		assertTrue( DECIMAL.matcher("1.234e12").matches() ) ;
		assertTrue( DECIMAL.matcher("1.234e+12").matches() ) ;
		assertTrue( DECIMAL.matcher("1.234e-12").matches() ) ;

		assertTrue( DECIMAL.matcher("123E12").matches() ) ;
	}
}
