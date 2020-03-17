package de.ecodia.simplequeue;

import javax.sql.DataSource;
import java.util.logging.Logger;

import static de.ecodia.simplequeue.Utils.spawnThread;

public class Queue implements Publisher, Subscriber {

	private static final Logger log = Logger.getLogger(Queue.class.getName());

	private static final String DEFAULT_TABLE_NAME = "simple_queue";

	private DataSource ds;

	private String hostId;

	private String tableName;

	private boolean stop = false;

	private boolean notifyProcessing = true;

	public Queue(DataSource ds) {
		this(ds, true);
	}

	public Queue(DataSource ds, boolean notifyProcessing) {
		this(ds, Utils.getHostId(), notifyProcessing, DEFAULT_TABLE_NAME);
	}

	public Queue(DataSource ds, String hostId, boolean notifyProcessing, String tableName) {
		this.ds = ds;

		this.hostId = hostId;
		this.notifyProcessing = notifyProcessing;
		this.tableName = tableName;
		Schema.createSchema(ds, tableName);
	}

	public void publish(String queue, String message) {

		var INSERT_QUERY = "INSERT INTO \"%s\" (publisher_id, queue, message) values (?, ?, ?);";

		try(
			var con = ds.getConnection();
			var stmt = con.prepareStatement(String.format(INSERT_QUERY, tableName));
		) {
			con.setAutoCommit(false);
			stmt.setString(1, hostId);
			stmt.setString(2, queue);
			stmt.setString(3, message);
			stmt.execute();
			con.commit();
		}
		catch(Exception e) {
			log.warning("Could not publish message: queue=" + queue + " message=" + message + " : " + e.getMessage());
		}
	}

	public void stop() {
		this.stop = true;
	}

	public void subscribe(String queue, MessageCallback callback) {

		var PENDING_QUERY = "SELECT * FROM \"%s\" WHERE status = 'PENDING' AND queue = ? order by id asc LIMIT 1 FOR UPDATE;";
		var UPDATE_PROCESSING_QUERY = "UPDATE \"%s\" SET status = ?, subscriber_id = ?  WHERE id = ?";
		var UPDATE_FINISHED_QUERY = "UPDATE \"%s\" SET status = ?, status_text = ?, subscriber_id = ?  WHERE id = ?";

		spawnThread(() -> {

				while(!this.stop) {
					try(
						var con = ds.getConnection();
						var stmtPending = con.prepareStatement(String.format(PENDING_QUERY, tableName));
						var processingStmt = con.prepareStatement(String.format(UPDATE_PROCESSING_QUERY, tableName));
						var finishStmt = con.prepareStatement(String.format(UPDATE_FINISHED_QUERY, tableName));
					) {
						con.setAutoCommit(false);
						stmtPending.setString(1, queue);
						var result = stmtPending.executeQuery();
						if(result.next()) {
							var message = Message.fromResultSet(result);

							if(notifyProcessing) {
								processingStmt.setString(1, MessageStatus.PROCESSING.toString());
								processingStmt.setString(2, hostId);
								processingStmt.setLong(3, message.getId());
								processingStmt.executeUpdate();
								con.commit();
							}

							try {
								callback.handle(message);
								finishStmt.setString(1, MessageStatus.FINISHED.toString());
								finishStmt.setString(2, "");
							}
							catch(CallbackFailedException e) {
								finishStmt.setString(1, MessageStatus.FAILED.toString());
								finishStmt.setString(2, e.getMessage());
							}
							catch(Exception e) {
								finishStmt.setString(1, MessageStatus.ERROR.toString());
								finishStmt.setString(2, e.getMessage());
							}

							finishStmt.setString(3, hostId);
							finishStmt.setLong(4, message.getId());
							finishStmt.executeUpdate();
							con.commit();
						}
						else {
							// if there is no new message we wait a grace period
							Utils.sleep(100);
						}
					}
					catch(Exception e) {
						log.warning("Could not read message: queue=" + queue + " : " + e.getMessage());
						// if there is an error in processing we wait 1 second to not overload
						Utils.sleep(1000);
					}
				}
			}

		);
	}
}
