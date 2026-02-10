package net.ptidej.jpms.project1.api1;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;

public class Project1API1 {

	public void publicMethod() {
		final Bag<String> bag = new HashBag<>();
		bag.add("Roy Batty");
		bag.add("Rick Deckard");
		bag.add("Rachael");

		System.out.println(bag);
		System.out.println();
	}

	protected void protectedMethod() {
		final Bag<String> bag = new HashBag<>();
		bag.add("Roy Batty");
		bag.add("Rick Deckard");
		bag.add("Rachael");

		System.out.println(bag);
		System.out.println();
	}

	void defaultMethod() {
		final Bag<String> bag = new HashBag<>();
		bag.add("Roy Batty");
		bag.add("Rick Deckard");
		bag.add("Rachael");

		System.out.println(bag);
		System.out.println();
	}

	@SuppressWarnings("unused")
	private void privateMethod() {
		final Bag<String> bag = new HashBag<>();
		bag.add("Roy Batty");
		bag.add("Rick Deckard");
		bag.add("Rachael");

		System.out.println(bag);
		System.out.println();
	}

}
