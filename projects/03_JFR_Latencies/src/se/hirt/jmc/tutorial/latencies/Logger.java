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
package se.hirt.jmc.tutorial.latencies;

/**
 * This class contains our problematic logger.
 */
public class Logger {
	private static Logger myInstance = new Logger();

	public static Logger getLogger() {
		return myInstance;
	}

	public synchronized void log(String text) {
		LogEvent event = new LogEvent(text);
		event.begin();
		// Do logging here
		// Write the text to a database or similar...
		try {
			// Simulate that this takes a little while.
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// Don't care.
		}
		event.end();
		event.commit();
	}
}
