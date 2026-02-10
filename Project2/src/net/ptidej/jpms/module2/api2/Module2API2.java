package net.ptidej.jpms.module2.api2;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

import net.ptidej.jpms.module1.api1.Module1API1;

/**
 * Nothing to change here
 */
public class Module2API2 extends Module1API1 {

	public void method1() {
		super.method1();
	}

	protected void method2() {
		super.method2();
	}

	@SuppressWarnings("unused")
	private void method3() {
		System.out.println(Module1API1.getMFQN(this));

		final Bag<String> bag = new HashBag<>();
		bag.add("Roy Batty");
		bag.add("Rick Deckard");
		bag.add("Rachael");

		System.out.println(bag);
		System.out.println();
	}

}
