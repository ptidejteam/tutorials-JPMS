package net.ptidej.jpms.project1.api2;

import net.ptidej.jpms.project1.api1.Project1API1;

public class Project1API2 extends Project1API1 {

	@SuppressWarnings("null")
	public void publicMethod() {
		final Project1API1 module1API1 = null;
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
