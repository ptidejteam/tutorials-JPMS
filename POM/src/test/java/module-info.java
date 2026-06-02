open module POM.test {
    requires POM;
    requires CPL;
    requires PADL;
    //requires PADL_Creator_ClassFile;
    //requires padl.generator;
    requires CFParse;
    requires java.base;
    requires java.xml;
    requires junit;
	requires org.apache.commons.lang3;

	requires PADL_Creator_ClassFile;
	requires PADL_Creator_Cpp_ANTRL;
    requires PADL_Generator_WIP;
}