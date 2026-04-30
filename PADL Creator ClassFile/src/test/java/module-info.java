module PADL_Creator_ClassFile.test {
	requires PADL_Creator_ClassFile;
	requires CPL;
	requires org.apache.commons.lang3;
	requires java.base;
	requires java.xml;
	requires org.apache.logging.log4j;
	requires org.apache.commons.io;
	requires org.apache.bcel;
	requires com.ibm.toad.cfparse;

	requires PADL;
	requires junit;
	
	exports padl.creator.classfile.test;
	exports padl.creator.classfile.helper;
	// opens padl.creator.classfile.test;
}