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
package se.hirt.jmc.tutorial.exceptions;

/**
 * Example which will throw two different kinds of exceptions.
 */
public final class ExceptionThrower implements Runnable {
	// The poor lab laptops will probably need one spare
	// hardware thread.
	private static final int THREADS = Runtime.getRuntime().availableProcessors() - 1;
	public long scaryCounter;

	public static void main(String[] args) throws Exception {
		System.out.println("Starting " + THREADS + " exception throwing threads!");
		for (int i = 0; i < THREADS; i++) {
			Thread t = new Thread(new ExceptionThrower());
			t.setDaemon(true);
			t.start();
		}
		System.out.println("Press <enter> to quit!");
		System.in.read();
	}

	private void loop() {
		while (true) {
			try {
				doStuff();
			} catch (Exception e) {
				// Evilly swallow the exception.
			}
			sleep(1);
		}
	}

	private void doStuff() throws Exception {
		// Having a few frames on the stack makes the traces, um, more interesting.
		if (isScary()) {
			doScaryThing();
		} else {
			throwMe();
		}
	}

	private void doScaryThing() throws ScaryException {
		throw new ScaryException("Really quite scary exception message!");
	}

	private boolean isScary() {
		return ++scaryCounter % 10 == 0;
	}

	private void throwMe() throws ExceptionThrowerException {
		throw new ExceptionThrowerException("Just your average exception message.");
	}

	@Override
	public void run() {
		loop();
	}

	private static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
