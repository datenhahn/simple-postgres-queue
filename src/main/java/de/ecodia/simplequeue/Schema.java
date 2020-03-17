package de.ecodia.simplequeue;

import javax.sql.DataSource;
import java.util.logging.Logger;

public class Schema {

	private static final Logger log = Logger.getLogger(Schema.class.getName());

	private static String CREATE_TABLE = "create table if not exists \"%s\" "
		+ "("
		+ "  id serial not null constraint \"%s_pk\" primary key,"
		+ "  queue varchar(255),"
		+ "  created timestamp default CURRENT_TIMESTAMP,"
		+ "  updated timestamp default CURRENT_TIMESTAMP,"
		+ "  publisher_id varchar(255) default '',"
		+ "  subscriber_id varchar(255) default '',"
		+ "  status varchar(30) default 'PENDING',"
		+ "  status_text varchar(1024) default '',"
		+ "  message text"
		+ ");";
	private static String CREATE_INDICES =  "create index if not exists \"%s_queue\" on \"%s\" (queue);"
		+ "create index if not exists \"%s_status\" on \"%s\" (status);";

	public static void createSchema(DataSource ds, String tableName) {
		try(
			var con = ds.getConnection();
		) {
			var stmt = con.createStatement();
			var ddl = String.format(CREATE_TABLE, tableName, tableName);
			var ddl2 = String.format(CREATE_INDICES, tableName, tableName, tableName, tableName);
			stmt.execute(ddl);
			stmt.execute(ddl2);
		}
		catch(Exception e) {
			log.severe("Could not create table: " + e.getMessage());
			throw new RuntimeException(e);
		}

	}
}
