module PADL_Creator_ClassFile {
	
	requires CPL;
	requires java.desktop;
	requires org.apache.commons.lang3;
	requires CFParse;
	requires PADL;
	
	exports padl.creator.classfile.relationship;
	exports padl.creator.classfile.util;
	exports padl.creator.classfile;
	}