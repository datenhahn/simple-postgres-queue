package de.ecodia.simplequeue;

/**
 * The CallbackFailedException should be used by MessageCallback
 * functions to indicate a controlled failure. The exception
 * message will be recorded in the database and can give a
 * first hint for debugging.
 */
public class CallbackFailedException extends RuntimeException {
	public CallbackFailedException(String message) {
		super(message);
	}

	public CallbackFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CallbackFailedException(Throwable cause) {
		super(cause);
	}

	public CallbackFailedException(String message, Throwable cause,
		boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
