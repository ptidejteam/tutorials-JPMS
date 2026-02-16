module Module1 {
	requires transitive org.apache.commons.collections4;

	exports net.ptidej.jpms.module1.api1;
	opens net.ptidej.jpms.module1.api1;
}