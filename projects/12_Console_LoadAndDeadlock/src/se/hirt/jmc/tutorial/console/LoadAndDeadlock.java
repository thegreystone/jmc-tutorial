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
package se.hirt.jmc.tutorial.console;

import java.io.IOException;

/**
 * Example which every once in a while will put a little load on the machine. It will also deadlock
 * two of the threads.
 */
public class LoadAndDeadlock {

	private static class AllocThread extends Thread {
		public void run() {
			while (true) {
				Thread.yield();
				try {
					sleep(20 * 1000);
				} catch (Exception e) {
				}

				for (int i = 0; i < 40000; i++) {
					char[] tmp = new char[1024 * 1024];
					tmp[1] = 'a';
				}
			}
		}
	}

	private static class LockerThread extends Thread {
		Object l1;
		Object l2;

		public void init(Object lock1, Object lock2) {
			l1 = lock1;
			l2 = lock2;
		}

		public void run() {
			while (true) {
				synchronized (l1) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					synchronized (l2) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						System.out.println("Got one!");
					}
				}
			}
		}

	}

	public static void main(String[] args) throws IOException {
		AllocThread allocthread = new AllocThread();
		allocthread.setDaemon(true);
		Object lock1 = new Object();
		Object lock2 = new Object();

		LockerThread first = new LockerThread();
		LockerThread second = new LockerThread();
		first.setDaemon(true);
		second.setDaemon(true);

		first.init(lock1, lock2);
		second.init(lock2, lock1);

		allocthread.start();
		first.start();
		second.start();

		System.out.print("Press enter to quit!");
		System.out.flush();
		System.in.read();
	}
}
