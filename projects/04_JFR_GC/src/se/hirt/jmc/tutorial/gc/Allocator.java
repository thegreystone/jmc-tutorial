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
package se.hirt.jmc.tutorial.gc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Will allocate a map with ExampleMapContent, and then proceeds to check for each ExampleMapContent
 * in the map, that it is really, really in the map. Over and over. ;)
 */
public final class Allocator implements Runnable {
	private final static int SET_SIZE = 10_000;
	private final Map<Integer, ExampleMapContent> map;

	public Allocator() {
		map = createMap(SET_SIZE);
	}

	@Override
	public void run() {
		long yieldCounter = 0;
		while (true) {
			Collection<ExampleMapContent> myAllocSet = map.values();
			for (ExampleMapContent c : myAllocSet) {
				if (!map.containsKey(c.getId()))
					System.out.println("Now this is strange!");
				if (++yieldCounter % 1000 == 0)
					Thread.yield();
			}
		}
	}

	private static Map<Integer, ExampleMapContent> createMap(int count) {
		Map<Integer, ExampleMapContent> map = new HashMap<>();
		for (int i = 0; i < count; i++) {
			map.put(i, new ExampleMapContent(i));
		}
		return map;
	}
}
