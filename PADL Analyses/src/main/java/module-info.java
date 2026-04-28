open module PADL_Analyses {
	//requires transitive PADL.Design.Motifs;
	requires CPL;
	requires PADL;
	requires PADL_Design_Motifs;
	requires com.ibm.toad.cfparse;
	requires java.desktop;
	requires java.sql;
	requires org.apache.bcel;
	
	exports padl.analysis.plantUMLGenerator;
	exports padl.analysis;
	exports padl.analysis.repository;
	exports padl.analysis.repository.aacrelationships;
	exports padl.analysis.repository.modelannotatorloc;
	exports padl.analysis.repository.systematicuml;
}