module PADL_Creator_ClassFile.test {
	
	requires PADL_Creator_ClassFile;
	requires CPL;
	requires org.apache.commons.lang3;
	requires org.apache.bcel;
	requires CFParse;
	requires PADL;
	requires junit;

	
	exports padl.creator.classfile.test; // TODO Rework the tests so that these exports can be removed... > Tahereh: I tried but the test failed
	exports padl.creator.classfile.helper;
}