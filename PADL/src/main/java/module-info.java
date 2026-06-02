module PADL {
	requires CPL;
	requires java.desktop;
	requires org.apache.commons.lang3;
	requires java.base; // Shouldn't be necessary
	requires java.xml;
	requires org.apache.logging.log4j;
	requires org.apache.commons.io;
	requires org.apache.bcel;
	requires CFParse;

	exports padl.util.adapter;
	exports padl.path;
	exports padl.kernel;
	exports padl.kernel.impl;
	exports padl.visitor.repository;
	exports padl.kernel.exception;
	exports padl.util;
	exports padl.event;
	exports padl.visitor;
}