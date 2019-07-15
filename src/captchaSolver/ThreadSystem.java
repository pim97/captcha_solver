package captchaSolver;

import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.apache.commons.lang3.ThreadUtils;

public class ThreadSystem {

	public static Hashtable<String, Thread> addedThreadsToProgram = new Hashtable<String, Thread>();

	public static Hashtable<String, ThreadObject> threads = new Hashtable<String, ThreadObject>();

	public static Hashtable<String, Thread> getAddedThreadsToProgram() {
		return addedThreadsToProgram;
	}

	public static int getAddedThreadsToProgramAndAlive() {
		int total = 0;
		Hashtable<String, Thread> list = (Hashtable<String, Thread>) ThreadSystem.getAddedThreadsToProgram().clone();
		for (Entry<String, Thread> entry : list.entrySet()) {
			String base = entry.getKey();
			Thread t = entry.getValue();

			if (t.isAlive()) {
				total++;
			}
		}
		return total;
	}

	public static void addAddedToThread(String urlWithUnique, Thread thread) {
		System.out.println("Added " + urlWithUnique + " and " + thread);
		getAddedThreadsToProgram().put(urlWithUnique, thread);

		// listThreads();
	}

	public static boolean setMaxThreadsBasedOnName(String keyName, int amount) {
		for (Entry<String, ThreadObject> entry : ThreadSystem.threads.entrySet()) {
			String key = entry.getKey();
			ThreadObject value = entry.getValue();

			if (keyName != null && keyName.contains(key)) {
				value.setMaxThreads(amount);
				entry.setValue(value);
				System.out.println("set max threads for: " + keyName + " to " + entry.getValue().getMaxThreads());
				return true;
			}
		}
		return false;
	}

	public static void stopThreadsByKey(String key) {
		Hashtable<String, Thread> list = (Hashtable<String, Thread>) ThreadSystem.getAddedThreadsToProgram().clone();
		for (Entry<String, Thread> entry : list.entrySet()) {
			String base = entry.getKey();
			Thread t = entry.getValue();

			if (base.contains(key)) {
				System.out.println("Stopped thread: " + base);
				t.stop();
			}
		}
	}

	public static boolean isThreadAliveAndRunning(String key) {
		Thread found = null;
		for (Thread t : ThreadUtils.getAllThreads()) {
			if (t.getName().equalsIgnoreCase("key")) {
				found = t;
			}
		}
		if (found != null) {
			if (!found.isAlive()) {
				return false;
			}
			if (found.isAlive()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isThreadAliveAndRunning(String key, BigInteger uniqueIdentifier) {
		Hashtable<String, Thread> list = (Hashtable<String, Thread>) ThreadSystem.getAddedThreadsToProgram().clone();
		for (Entry<String, Thread> entry : list.entrySet()) {
			String base = entry.getKey();
			Thread t = entry.getValue();

			if ((base.contains(key) && base.contains(uniqueIdentifier.toString())) && (!t.isAlive())) {
				return false;
			}
		}
		return true;
	}

	public static boolean isThreadAliveAndRunningWithoutUnique(String key) {
		boolean found = false;
		Hashtable<String, Thread> list = (Hashtable<String, Thread>) ThreadSystem.getAddedThreadsToProgram().clone();
		for (Entry<String, Thread> entry : list.entrySet()) {
			String base = entry.getKey();
			Thread t = entry.getValue();

			if (base.equalsIgnoreCase(key) && t.isAlive()) {
				found = true;
			}
		}
		return found;
	}

	public static void listThreads() {
		Hashtable<String, Thread> list = (Hashtable<String, Thread>) ThreadSystem.getAddedThreadsToProgram().clone();
		for (Entry<String, Thread> entry : list.entrySet()) {
			String base = entry.getKey();
			Thread t = entry.getValue();

			System.out.println("listing: " + base + " " + t.getName() + " alive: " + t.isAlive());
		}
	}

	public static int getThreadsActiveByWebsite(String key) {
		int count = 0;
		Hashtable<String, Thread> list = (Hashtable<String, Thread>) ThreadSystem.getAddedThreadsToProgram().clone();
		for (Entry<String, Thread> entry : list.entrySet()) {
			String base = entry.getKey();
			Thread thread = entry.getValue();

			if (base.contains(key) && thread.isAlive()) {
				count++;
			}
		}
		System.out.println("Found thread " + key + " for total: " + count);
		return count;
	}

	public static void main(String args[]) {

	}
}
