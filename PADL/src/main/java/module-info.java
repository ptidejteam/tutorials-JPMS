module PADL {
	exports padl.kernel.impl;
	exports padl.util.adapter;
	exports padl.path;
	exports padl.kernel;
	exports padl.visitor.repository;
	exports padl.kernel.exception;
	exports padl.util;
	exports padl.event;
	exports padl.visitor;

	requires CPL;
	requires com.ibm.toad.cfparse;
	requires java.desktop;
	requires java.base;
	requires org.apache.commons.lang3;
	requires junit;
}