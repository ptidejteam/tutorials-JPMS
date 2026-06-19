 module PADL_Generator_WIP {
	
	requires CPL;
	requires PADL;
	requires PADL_Analyses;
	requires PADL_Creator_AspectJ;
	requires PADL_Creator_ClassFile;
	requires PADL_Creator_Cpp_ANTRL;
	requires PADL_Statements_Creator_ClassFile;
	
	exports padl.generator.helper;
}