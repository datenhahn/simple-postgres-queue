package de.ecodia.simplequeue;

public interface MessageCallback {
	String handle(Message message);
}
