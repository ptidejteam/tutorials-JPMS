module PADL_Creator_Cpp_ANTRL {
	exports padl.creator.cppfile.antlr;
	exports padl.creator.cppfile.antlr.parser;
	exports padl.kernel.cpp.antlr.impl;
	exports padl.creator.cppfile.antlr.misc;
	exports padl.kernel.cpp.antlr;

	requires CPL;
	requires PADL;
	requires com.ibm.toad.cfparse;
	requires javacc;
}