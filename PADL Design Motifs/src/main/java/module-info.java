module PADL_Design_Motifs {
	exports padl.motif.models;
	exports padl.motif.repository;
	exports padl.motif;
	exports padl.motif.visitor;
	exports padl.motif.detector.repository;
	exports padl.motif.detector;
	exports padl.motif.util.adapter;
	exports motifs.padl.event;
	exports padl.motif.kernel;
	exports padl.motif.kernel.impl;

	requires CPL;
	requires PADL;
	requires com.ibm.toad.cfparse;
	requires java.desktop;
	requires org.apache.commons.lang3;
}