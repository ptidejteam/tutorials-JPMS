module PADL_Statements_Creator_ClassFile {
	requires CPL;
	requires PADL;
	requires PADL_Analyses;
	requires PADL_Statements;
	requires CFParse;

	
	exports ptidej.statement.creator.classfiles.loc;
	exports padl.statement.creator.classfiles;
	exports ptidej.statement.creator.classfiles.conditionals;

	
}