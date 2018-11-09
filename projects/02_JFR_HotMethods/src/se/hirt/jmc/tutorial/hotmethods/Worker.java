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

/**
 * The code run in the worker threads. One JFR event will be generated per lap in the loop,
 * recording the "result".
 */
public class Worker implements Runnable {
	public void run() {
		while (true) {
			WorkEvent event = new WorkEvent();
			event.begin();
			HolderOfUniqueValues i1 = new HolderOfUniqueValues();
			HolderOfUniqueValues i2 = new HolderOfUniqueValues();
			i1.initialize(3);
			i2.initialize(5);
			int intersectionSize = i1.countIntersection(i2);
			event.setIntersectionSize(intersectionSize);
			event.end();
			event.commit();
			Thread.yield();
		}
	}
}
