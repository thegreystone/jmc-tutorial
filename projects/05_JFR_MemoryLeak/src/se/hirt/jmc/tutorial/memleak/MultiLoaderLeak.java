/*
 * Copyright (c) 2018, Marcus Hirt
 * 
 * jmc-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jmc-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jmc-tutorial. If not, see <http://www.gnu.org/licenses/>.
 */

package se.hirt.jmc.tutorial.memleak;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Creates a memory leak in three different class loaders, two leaking at different speeds, and one
 * not leaking at all.
 */
public class MultiLoaderLeak {
	public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// Create three different loaders, A, B and C.
		// Load the Leak class in all three separately, and kick off two leaking, 
		// and one that isn't.
		CustomLoader loaderA = new CustomLoader("A");
		CustomLoader loaderB = new CustomLoader("B");
		CustomLoader loaderC = new CustomLoader("C");
		runMethod("startLeak", loaderA.loadClass("Leak"));
		runMethod("startNonLeak", loaderB.loadClass("Leak"));
		runMethod("startFastLeak", loaderC.loadClass("Leak"));
	}

	private static void runMethod(String method, Class<?> clazz) throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Method m = clazz.getMethod(method, new Class<?>[0]);
		m.invoke(null, new Object[0]);
	}
}
