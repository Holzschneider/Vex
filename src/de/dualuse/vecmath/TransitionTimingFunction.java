package de.dualuse.vecmath;

import static java.lang.Math.ceil;
import static java.lang.Math.max;
import static java.lang.Math.min;

public interface TransitionTimingFunction {
	double f(double t);

	static final TransitionTimingFunction Linear = new TransitionTimingFunction() {
		public double f(double t) { return t; } //min(max(t,0.0),1.0); };
	};


	static final TransitionTimingFunction EaseInOut = new TransitionTimingFunction() {
		public double f(double t) {
			t = min(max(t,0.0),1.0);
			if ((t/=1./2.) < 1.) return 1./2.*t*t;
			return -1./2 * ((--t)*(t-2) - 1);
		};
	};

	
	static final TransitionTimingFunction EaseOut = new TransitionTimingFunction() {
		public double f(double t) {
			if (t>1.) return 1.;
			return - (t-1)*(t-1.) + 1.;
		};
	};

	static final TransitionTimingFunction Ease = EaseOut;

	
	static final TransitionTimingFunction EaseIn = new TransitionTimingFunction() {
		public double f(double t) {
			if (t>1.) return 1.;
			return t*t;
		};
	};
	
	
	static final TransitionTimingFunction StepStart = new TransitionTimingFunction() {
		public double f(double t) {
			return t>0?1:0;
		}
	};
	
	static final TransitionTimingFunction StepEnd = new TransitionTimingFunction() {
		public double f(double t) {
			return t<1?0:1;
		}
	};
	
//	static class CubicBezier implements TransitionTimingFunction {
//		@Override
//		public double f(double t) {
//			return 0;
//		}
//		
//		static public CubicBezier with( double a, double da, double db, double b ) {
//			
//		}
//	}
	
	static class Steps implements TransitionTimingFunction {
		final int n;
		
		public Steps(int n) { this.n = n; }
		
		@Override
		public double f(double t) {
			t = min(max(t,0.0),1.0);
			return ceil(t*n)/n;
		}
		
		static Steps count(int n) { 
			return new Steps(n);
		}
	}
	
	
	
	////////////
	
	static final TransitionTimingFunction BounceOut = new TransitionTimingFunction() {
		public double f(double t) {
			final double d = 1.,c=1.,b=0.; 
			
			if ((t/=d) < (1/2.75)) {
				return c*(7.5625*t*t) + b;
			} else if (t < (2/2.75)) {
				return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
			} else if (t < (2.5/2.75)) {
				return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
			} else {
				return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
			}
		};
	};
	
	static final TransitionTimingFunction ElasticOut = new TransitionTimingFunction() {
		
		public double f(double t) {
			final double d = 1.,c=1.,b=0.;
			double p=0.5, a=0.1;
			double s=0;
			
			if (t==0) return b;  if ((t/=d)==1) return b+c;  if (p==0) p=d*.3;
			if (a==0 || a < Math.abs(c)) { a=c; s=p/4; }
			else s = p/(2*Math.PI) * Math.asin (c/a);
			
			return (a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b);
		};
	};
	
}


