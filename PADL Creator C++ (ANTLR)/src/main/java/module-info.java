module PADL_Creator_Cpp_ANTRL {
	
	requires CPL;
	requires PADL;
	requires CFParse;
	requires javacc;

	exports padl.creator.cppfile.antlr;
	exports padl.creator.cppfile.antlr.parser;
	exports padl.kernel.cpp.antlr.impl;
	exports padl.creator.cppfile.antlr.misc;
	exports padl.kernel.cpp.antlr;
}