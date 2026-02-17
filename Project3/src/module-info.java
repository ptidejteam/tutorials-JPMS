module Module3 {
	requires Module2;
	requires org.junit.jupiter.api;

	// Only required to run/pass the test in Maven
	exports net.ptidej.jpms.module3 to org.junit.platform.commons;
}