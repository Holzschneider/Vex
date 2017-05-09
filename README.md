# Vex
Vector Algebra for Java (& soon Javascript) optimized for brevity, transparency and ease-of-use instead of raw speed.

Release
-------

The current state of the project is to be considered "incomplete and in development", so no releases to any
maven repository are done yet. To add *Vex* as a dependency in your maven project checkout, build and install the 
latest version to your system

	$ git clone https://github.com/Holzschneider/Vex.git && cd Vex && mvn install

and modify your *dependencies* section accordingly.
 
	  <dependencies>
	  ...
	  	<dependency>
	  		<groupId>de.dualuse</groupId>
	  		<artifactId>Vex</artifactId>
	  		<version>[0,)</version>
	  	</dependency>
	  ...
	  </dependencies>

	
FAQ
--------------------

*Design Decisions*

**doubles only**: (a) yields simpler code for JavaScript, (b) code-inlined vector algebra is expected to never be a 
performance bottleneck, (c) serious bulk vector operations are supposed to be done with serious bulk vector processing
libraries, or shall be off-loaded to CUDA/OpenCL/Compute-whatever (d) shut up, already.

