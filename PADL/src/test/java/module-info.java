open module PADL.test {
	requires PADL;
	
	requires CPL;
	requires java.desktop;
	requires org.apache.commons.lang3;
	requires java.base;
	requires java.xml;
	requires org.apache.logging.log4j;
	requires org.apache.commons.io;
	requires org.apache.bcel;

	requires com.ibm.toad.cfparse;
	
	requires junit;
	
	exports padl.test.helper;
}