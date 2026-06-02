open module SAD.test {
	requires SAD;
    requires CPL;
    requires PADL;
    //requires PADL_Creator_ClassFile;
    //requires padl.generator;
    requires CFParse;
    requires java.base;
    requires java.xml;
    requires junit;
    requires org.apache.commons.lang3;

    requires PADL_Analyses;
    requires PADL_Creator_ClassFile;
    requires PADL_Statements_Creator_ClassFile;
    requires PADL_Generator_WIP;
}