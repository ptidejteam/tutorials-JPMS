module PADL_Creator_ClassFile.test {
	
	requires PADL_Creator_ClassFile;
	requires CPL;
	requires org.apache.commons.lang3;
	requires org.apache.bcel;
	requires CFParse;
	requires PADL;
	requires junit;
	//requires java.base;
	//requires java.xml;
	//requires org.apache.logging.log4j;
	//requires org.apache.commons.io;
	
	exports padl.creator.classfile.test; // TODO Rework the tests so that these exports can be removed...
										//Tahereh: I tried but the test failed
	exports padl.creator.classfile.helper;
	// opens padl.creator.classfile.test;
}