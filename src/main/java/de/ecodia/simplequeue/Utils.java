package de.ecodia.simplequeue;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.util.UUID;
import java.util.logging.Logger;

public class Utils {

	private static final Logger log = Logger.getLogger(Utils.class.getName());

	public static String getHostId() {

		var hostId = "error";

		try {
			hostId = InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e) {
			hostId = "unknown" + UUID.randomUUID();;
			log.warning("Could not determine hostname, using this random id: " + hostId);
		}

		return hostId;
	}

	public static void sleep(int timeMillis) {
		try {
			Thread.sleep(timeMillis);
		}
		catch(InterruptedException e) {
			log.severe(e.getMessage());
		}
	}

	public static void spawnThread(Runnable runnable) {
		var thread = new Thread(runnable);
		thread.start();
	}
}
