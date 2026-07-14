/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.kernel.impl.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

import com.ibm.toad.cfparse.utils.Access;

import junit.framework.TestCase;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import padl.kernel.impl.Class;
import padl.kernel.impl.FirstClassEntity;

/**
 * Tests contract for a MethodInvocation.
 * 
 * @author Stephane Vaucher
 * @since  2005/11/25
 */
public class MethodInvocationTest extends TestCase {
	private Constructor methodInvocationConstructor;
	public MethodInvocationTest(final String aName) {
		super(aName);
	}
	
	public void setUp() throws Exception {
		super.setUp();
		java.lang.Class intClass = int.class;
		java.lang.Class firstClassEntityClass = IFirstClassEntity.class;
		this.methodInvocationConstructor = java.lang.Class
				.forName("padl.kernel.impl.MethodInvocation")
				.getDeclaredConstructor(intClass, intClass, intClass, firstClassEntityClass);
		this.methodInvocationConstructor.setAccessible(true);
	}
	

	/*
	 * Test method for 'padl.kernel.impl.MethodInvocation.equals(IMethodInvocation)'
	 */
	public void testEqualsIMethodInvocation() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		IFirstClassEntity firstClassEntity =
			new FirstClassEntity("foo".toCharArray()) {
				private static final long serialVersionUID =
					-2438802743951158575L;
			};

		final Object methodInv1 =
			this.methodInvocationConstructor.newInstance(
				IMethodInvocation.INSTANCE_CLASS,
				0,
				Access.ACC_PUBLIC,
				firstClassEntity);
		final Object methodInv2 =
			this.methodInvocationConstructor.newInstance(
				IMethodInvocation.INSTANCE_CLASS,
				0,
				Access.ACC_PUBLIC,
				firstClassEntity);

		assertEquals(
			"Methods with same target and type should be equivalent.",
			methodInv1,
			methodInv2);

		HashSet set = new HashSet(2);
		set.add(methodInv1);
		set.add(methodInv2);

		assertEquals(
			"Two equals method invocations cannot coexist in a set.",
			1,
			set.size());
	}
}
