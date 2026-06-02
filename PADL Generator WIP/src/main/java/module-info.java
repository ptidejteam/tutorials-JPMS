open module PADL_Generator_WIP {
	requires CPL;
	requires java.desktop;
	requires org.apache.commons.lang3;
	requires java.base;
	requires java.xml;
	requires org.apache.logging.log4j;
	requires org.apache.commons.io;
	requires org.apache.bcel;
	requires CFParse;
	requires PADL;

	requires PADL_Analyses;
	requires PADL_Creator_AspectJ;
	requires PADL_Creator_ClassFile;
	requires PADL_Creator_Cpp_ANTRL;
	requires PADL_Statements;
	requires PADL_Statements_Creator_ClassFile;
		
	exports padl.generator.helper;	
}