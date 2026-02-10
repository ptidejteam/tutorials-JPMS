package net.ptidej.jpms.project1.api1;

public class Other {

	@SuppressWarnings("null")
	public void publicMethod() {
		final Project1API1 module1API1 = null;
		module1API1.publicMethod();
	}

	@SuppressWarnings("null")
	protected void protectedMethod() {
		final Project1API1 module1API1 = null;
		module1API1.protectedMethod();
	}

	@SuppressWarnings("null")
	void defaultMethod() {
		final Project1API1 module1API1 = null;
		module1API1.defaultMethod();
	}

	@SuppressWarnings("unused")
	private void privateMethod() {
		// Invisible
		//	final Project1API1 module1API1 = null;
		//	module1API1.privateMethod();
	}

}
