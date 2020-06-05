package de.ecodia.simplequeue;

/**
 * The PublishException is thrown when there is an error during publishing
 * it just wraps the thrown exception into a runtime exception
 */
public class PublishException extends RuntimeException {
	public PublishException(String message) {
		super(message);
	}

	public PublishException(String message, Throwable cause) {
		super(message, cause);
	}

	public PublishException(Throwable cause) {
		super(cause);
	}

	public PublishException(String message, Throwable cause,
		boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
