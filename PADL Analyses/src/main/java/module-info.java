module PADL_Analyses {
	
	requires CPL;
	requires PADL;
	requires PADL_Design_Motifs;
	requires CFParse;
	requires java.desktop;
	requires java.sql;

	
	exports padl.analysis;
	exports padl.analysis.plantUMLGenerator;
	exports padl.analysis.repository;
	exports padl.analysis.repository.aacrelationships;
	exports padl.analysis.repository.modelannotatorloc;
	exports padl.analysis.repository.systematicuml;
	
}