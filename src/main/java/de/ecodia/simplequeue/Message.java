package de.ecodia.simplequeue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The Message class defines the format of a queue message.
 * The message is only stored as string, the internal message
 * format is up to the developers.
 */
public class Message {

	private long id;

	private String queue;

	private LocalDateTime created;

	private LocalDateTime updated;

	private String publisherId;

	private String subscriberId;

	private MessageStatus status;

	private String statusText;

	private String message;

	public static Message fromResultSet(ResultSet resultSet) {
		try {
			return new Message(
				resultSet.getLong("id"),
				resultSet.getString("queue"),
				resultSet.getTimestamp("created").toLocalDateTime(),
				resultSet.getTimestamp("updated").toLocalDateTime(),
				resultSet.getString("publisher_id"),
				resultSet.getString("subscriber_id"),
				MessageStatus.valueOf(resultSet.getString("status")),
				resultSet.getString("status_text"),
				resultSet.getString("message")
			);
		}
		catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Message(long id, String queue, LocalDateTime created, LocalDateTime updated, String publisherId, String subscriberId,
		MessageStatus status, String statusText, String message) {
		this.id = id;
		this.queue = queue;
		this.created = created;
		this.updated = updated;
		this.publisherId = publisherId;
		this.subscriberId = subscriberId;
		this.status = status;
		this.statusText = statusText;
		this.message = message;
	}

	public Message() {

	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
