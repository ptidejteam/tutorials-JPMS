package net.ptidej.jpms.client;

public class Main {

	private static class SubProject1API2 extends net.ptidej.jpms.project1.api2.Project1API2 {
		protected void protectedMethod() {
			super.protectedMethod();
		}
	}

	private static class SubModule1API2 extends net.ptidej.jpms.module1.api2.Module1API2 {
		protected void protectedMethod() {
			super.protectedMethod();
		}
	}

	@SuppressWarnings("null")
	public static void main(final String[] args) {
		final net.ptidej.jpms.project1.api1.Project1API1 project1API1 = null;
		project1API1.publicMethod();

		final net.ptidej.jpms.project1.api2.Project1API2 project1API2 = null;
		project1API2.publicMethod();

		final SubProject1API2 subProject1API2 = null;
		subProject1API2.publicMethod();
		subProject1API2.protectedMethod();

		// Invisible
		//	final net.ptidej.jpms.module1.api1.Module1API1 module1API1 = null;
		//	project1API1.publicMethod();

		final net.ptidej.jpms.module1.api2.Module1API2 module1API2 = null;
		module1API2.publicMethod();

		final SubModule1API2 subModule2API2 = null;
		subModule2API2.publicMethod();
		subModule2API2.protectedMethod();
	}

}
