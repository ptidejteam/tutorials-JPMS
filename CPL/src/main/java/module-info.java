module CPL {
	requires java.xml;
	requires java.desktop;
	requires org.apache.logging.log4j;
	requires org.apache.commons.io;
	requires org.apache.bcel;

	requires com.ibm.toad.cfparse;
//	requires junit; // Should not be there, because should be only for tests!
//	// Besides, because it's here, we must change pom.xml so that JUnit appears in the compiler scope, not just test scope
//	// Moreover, JUnit 4.13.2 is not a module (no module-info.java), so Eclipse should report a warning for an automatic module name

	exports util.awt;
	exports util.help;
	exports util.lang;
	exports util.io;
	exports util.repository;
	exports util.repository.impl;
	exports util.multilingual;
}