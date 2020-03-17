package de.ecodia.simplequeue;

/**
 * The CallbackFailedException should be used by MessageCallback
 * functions to indicate a controlled failure. The exception
 * message will be recorded in the database and can give a
 * first hint for debugging.
 */
public class CallbackFailedException extends RuntimeException {
}
