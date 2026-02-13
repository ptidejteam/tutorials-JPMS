package net.ptidej.jpms.module3;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.HashBag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.ptidej.jpms.module1.api1.Module1API1;
import net.ptidej.jpms.module2.api2.Module2API2;

/**
 * Nothing to change here
 */
public class Main {

	public static void main(final String[] args) {
		final Main main = new Main();
		main.foo();
	}
	
	@Test
	public void foo() {
		final OutputStream os = new ByteArrayOutputStream();
		System.setOut(new PrintStream(os));

		final Module1API1 module1API1 = new Module1API1();
		module1API1.method1();

		final Module2API2 module2API2 = new Module2API2();
		module2API2.method1();

		try {
			final Class<Module1API1> module1API1Class = Module1API1.class;
			final Method module1API1Method3 = module1API1Class.getDeclaredMethod("method3", new Class[0]);
			module1API1Method3.setAccessible(true);
			module1API1Method3.invoke(module1API1, new Object[0]);
		} catch (final IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}

		System.out.println(Module1API1.getMFQN(this));

		final Bag<String> bag = new HashBag<>();
		bag.add("Roy Batty");
		bag.add("Rick Deckard");
		bag.add("Rachael");

		System.out.println(bag);

		// See https://stackoverflow.com/questions/11020893/remove-non-ascii-non-printable-characters-from-a-string
		// to understand the role of replaceAll("\\p{C}", "")
		Assertions.assertEquals(os.toString().replaceAll("\\p{C}", ""), """
				Module1.net.ptidej.jpms.module1.api1.Module1API1
				[1:Rick Deckard,1:Rachael,1:Roy Batty]

				Module2.net.ptidej.jpms.module2.api2.Module2API2
				[1:Rick Deckard,1:Rachael,1:Roy Batty]

				Module1.net.ptidej.jpms.module1.api1.Module1API1
				[1:Rick Deckard,1:Rachael,1:Roy Batty]

				Module3.net.ptidej.jpms.module3.Main
				[1:Rick Deckard,1:Rachael,1:Roy Batty]
				""".replaceAll("\\p{C}", ""));
	}

}
