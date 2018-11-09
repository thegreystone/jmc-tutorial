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
package se.hirt.jmc.tutorial.hotmethods;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Never mind that this is a ridiculous class to begin with. A one line change can make this example
 * run a lot faster.
 */
public class HolderOfUniqueValues {
	private Collection<Integer> collection;

	public HolderOfUniqueValues() {
		collection = new LinkedList<>();
	}

	// Hint: This creates a list of unique elements!
	public void initialize(int moduloDivisor) {
		collection.clear();
		for (int i = 1; i < 10000; i++) {
			if ((i % moduloDivisor) != 0)
				collection.add(i);
		}
	}

	protected Collection<Integer> getCollection() {
		return collection;
	}

	public int countIntersection(HolderOfUniqueValues other) {
		int count = 0;
		for (Integer i : collection) {
			if (other.getCollection().contains(i)) {
				count++;
			}
		}
		return count;
	}
}
