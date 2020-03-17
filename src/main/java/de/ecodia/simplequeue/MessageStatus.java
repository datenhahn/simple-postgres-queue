package de.ecodia.simplequeue;

public enum MessageStatus {
	PENDING("PENDING"),
	PROCESSING("PROCESSING"),
	FINISHED("FINISHED"),
	FAILED("FAILED"),
	ERROR("ERROR");

	private String status;

	MessageStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return status;
	}
}
