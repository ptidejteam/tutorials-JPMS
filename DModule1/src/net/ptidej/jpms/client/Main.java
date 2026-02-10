package net.ptidej.jpms.client;

/**
 * Nothing to change here
 */
public class Main {

	// Invisible
	// "A named module cannot, in fact, even declare a dependence upon the unnamed module."
	// https://openjdk.org/projects/jigsaw/spec/sotms/2016-03-08
	//	private static class SubProject1API2 extends net.ptidej.jpms.project1.api2.Project1API2 {
	//		protected void protectedMethod() {
	//			super.protectedMethod();
	//		}
	//	}

	// Invisible
	// Module on the classpath
	//	private static class SubModule1API2 extends net.ptidej.jpms.module1.api2.Module1API2 {
	//		protected void protectedMethod() {
	//			super.protectedMethod();
	//		}
	//	}

	public static void main(final String[] args) {
		// Invisible
		// "A named module cannot, in fact, even declare a dependence upon the unnamed module."
		// https://openjdk.org/projects/jigsaw/spec/sotms/2016-03-08
		//	final net.ptidej.jpms.project1.api1.Project1API1 project1API1 = null;
		//	project1API1.publicMethod();

		// Invisible
		// "A named module cannot, in fact, even declare a dependence upon the unnamed module."
		// https://openjdk.org/projects/jigsaw/spec/sotms/2016-03-08
		//	final net.ptidej.jpms.project1.api2.Project1API2 project1API2 = null;
		//	project1API2.publicMethod();

		// Moot, cf. above
		// final SubProject1API2 subProject1API2 = null;
		// subProject1API2.publicMethod();
		// subProject1API2.protectedMethod();

		// Invisible
		// final net.ptidej.jpms.module1.api1.Module1API1 module1API1 = null;
		// project1API1.publicMethod();

		// Invisible
		// Module on the classpath
		//	final net.ptidej.jpms.module1.api2.Module1API2 module1API2 = null;
		//	module1API2.publicMethod();

		// Invisible
		// Module on the classpath
		//	final SubModule1API2 subModule2API2 = null;
		//	subModule2API2.publicMethod();
		//	subModule2API2.protectedMethod();
	}

}
