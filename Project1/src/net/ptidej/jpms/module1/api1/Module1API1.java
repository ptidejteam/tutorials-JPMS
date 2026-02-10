package net.ptidej.jpms.module1.api1;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

/**
 * Nothing to change here
 */
public class Module1API1 {

	public static String getMFQN(final Object o) {
		return o.getClass().getModule().getName() + '.' + o.getClass().getCanonicalName();
	}

	public void method1() {
		System.out.println(Module1API1.getMFQN(this));

		final Bag<String> bag = new HashBag<>();
		bag.add("Roy Batty");
		bag.add("Rick Deckard");
		bag.add("Rachael");

		System.out.println(bag);
		System.out.println();
	}

	protected void method2() {
		System.out.println(Module1API1.getMFQN(this));

		final Bag<String> bag = new HashBag<>();
		bag.add("Roy Batty");
		bag.add("Rick Deckard");
		bag.add("Rachael");

		System.out.println(bag);
		System.out.println();
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
