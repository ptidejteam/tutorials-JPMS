package net.ptidej.jpms.module1.api2;

import net.ptidej.jpms.module1.api1.Module1API1;

public class Module1API2 extends Module1API1 {

	@SuppressWarnings("null")
	public void publicMethod() {
		final Module1API1 module1API1 = null;
		module1API1.publicMethod();
	}

	protected void protectedMethod() {
		super.protectedMethod();
	}

	void defaultMethod() {
		// Invisible
		//	final Module1API1 module1API1 = null;
		//	module1API1.defaultMethod();
		// Or
		//	super.defaultMethod();
	}

	@SuppressWarnings("unused")
	private void privateMethod() {
		// Invisible
		//	final Module1API1 module1API1 = null;
		//	module1API1.privateMethod();
		// Or
		//	super.privateMethod();
	}

}
