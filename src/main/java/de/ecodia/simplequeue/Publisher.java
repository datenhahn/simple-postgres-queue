package de.ecodia.simplequeue;

public interface Publisher {
	void publish(String queue, String message);
	void publish(String queue, String traceId, String message);
}
