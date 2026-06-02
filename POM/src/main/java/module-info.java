module POM {
	requires CPL;
	requires PADL;
	requires PADL_Statements;
	requires CFParse;
	requires org.apache.commons.lang3; 
	
	//exports pom.test.classfile.general;
	exports pom.util;
	//exports pom.test;
	//exports pom.test.classfile.specific;
	//exports pom.helper;
	exports pom.operators;
	exports pom.metrics;
	//exports pom.test.cppfile.general;
	exports pom.metrics.repository;
	exports pom.primitives;
	//exports pom.helper.xml;

	
}