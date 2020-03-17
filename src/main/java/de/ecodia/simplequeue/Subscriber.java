package de.ecodia.simplequeue;

public interface Subscriber {
	void subscribe(String queue, MessageCallback callback);
}
