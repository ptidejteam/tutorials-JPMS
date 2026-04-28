module CPL.test {	
	requires CPL;
	requires java.xml;
	requires java.desktop;
	requires org.apache.logging.log4j;
	requires org.apache.commons.io;
	requires org.apache.bcel;

	requires com.ibm.toad.cfparse;

	requires junit;
	
	// opens cpl.test to junit;
}