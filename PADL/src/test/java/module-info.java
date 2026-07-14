module PADL.test {
	
	requires PADL;
	requires CPL;
	requires java.desktop;
	requires org.apache.commons.lang3;
	requires org.apache.bcel;
	requires CFParse;
	requires junit;

	exports padl.test to junit;
	exports padl.kernel.impl.test to junit;
	exports padl.test.defaultpackage to junit;
	exports padl.test.listeners to junit;
}