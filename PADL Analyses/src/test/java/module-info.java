module PADL_Analyses.test {
	requires PADL_Creator_ClassFile;
	requires PADL_Analyses;
	requires CPL;
	requires PADL;
	requires PADL_Design_Motifs;
	requires com.ibm.toad.cfparse;
	requires java.desktop;
	requires java.sql;
	requires org.apache.bcel;
	requires PADL_Creator_ClassFile.test;
	requires PADL.test;
	
	//requires com.ibm.toad.cfparse.utils;
	
	requires static junit;
	
}