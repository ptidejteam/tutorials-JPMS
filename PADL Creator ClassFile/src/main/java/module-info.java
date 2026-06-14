module PADL_Creator_ClassFile {
	
	requires CPL;
	requires java.desktop;
	requires org.apache.commons.lang3;
	requires CFParse;
	requires PADL;
	//requires java.base;
	//requires java.xml;
	//requires org.apache.logging.log4j;
	//requires org.apache.commons.io;
	//requires org.apache.bcel;
	
	exports padl.creator.classfile.relationship;
	exports padl.creator.classfile.util;
	exports padl.creator.classfile;
	}