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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Custom loader to leak in multiple different class loaders.
 */
public class CustomLoader extends ClassLoader {
	private static final int BUFFER_SIZE = 4096;
	private final String name;
	private int loadedClasses;

	public CustomLoader(String classLoaderName) {
		this.name = classLoaderName;
	}

	protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class<?> clazz = findLoadedClass(name);
		if (clazz != null) {
			return clazz;
		}
		String classFileName = name.replace('.', '/') + ".class";
		byte[] classBytes = null;

		try {
			InputStream in = getResourceAsStream(classFileName);
			byte[] buffer = new byte[BUFFER_SIZE];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int n = -1;
			while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				out.write(buffer, 0, n);
			}
			classBytes = out.toByteArray();
		} catch (IOException e) {
			throw new ClassNotFoundException("Could not load the class " + name + " from " + classFileName, e);
		}

		if (classBytes == null) {
			throw new ClassNotFoundException("Cannot load the class " + name);
		}
		try {
			clazz = defineClass(name, classBytes, 0, classBytes.length);
			if (resolve) {
				resolveClass(clazz);
			}
			loadedClasses++;
		} catch (SecurityException e) {
			// Delegate to parent for system classes
			clazz = super.loadClass(name, resolve);
		}
		return clazz;
	}

	public int getNumberOfLoadedClasses() {
		return loadedClasses;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "CustomLoader [name=" + getName() + ", classes loaded=" + getNumberOfLoadedClasses() + "]";
	}
}
