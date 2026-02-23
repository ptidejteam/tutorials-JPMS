module CPL {
	requires java.xml;
	requires java.desktop;
	requires org.apache.logging.log4j;
	requires org.apache.commons.io;
	requires org.apache.bcel;
	requires cfparse;
	
	exports util.awt;
	exports util.help;
	exports util.lang;
}