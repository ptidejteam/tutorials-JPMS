module PADL_Creator_AspectJ {
	exports padl.creator.aspectjlst;
	exports padl.aspectj.kernel.exception;
	exports padl.aspectj.kernel.impl;
	exports padl.aspectj.kernel;
	exports padl.creator.aspectjlst.util;

	requires PADL;
	requires aspectjtools;
	requires com.ibm.toad.cfparse;
}