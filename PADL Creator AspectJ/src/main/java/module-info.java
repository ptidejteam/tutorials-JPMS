module PADL_Creator_AspectJ {
	
	requires PADL;
	requires aspectjtools;
	requires CFParse;

	exports padl.creator.aspectjlst;
	exports padl.aspectj.kernel.exception;
	exports padl.aspectj.kernel.impl;
	exports padl.aspectj.kernel;
	exports padl.creator.aspectjlst.util;
}