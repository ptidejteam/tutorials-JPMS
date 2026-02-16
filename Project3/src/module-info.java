module Module3 {
	requires Module2;
	requires org.junit.jupiter.api;
	exports net.ptidej.jpms.module3 to org.junit.platform.commons;
}