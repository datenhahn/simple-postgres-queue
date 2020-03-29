package de.ecodia.simplequeue.examples;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.ecodia.simplequeue.SimpleQueue;
import de.ecodia.simplequeue.Utils;

public class ExampleApp {
	public static void main(String[] args) {

		var config = new HikariConfig();
		config.setJdbcUrl("jdbc:postgresql://localhost/postgres");
		config.setUsername("postgres");
		config.setPassword("postgres");
		var dataSource = new HikariDataSource(config);

		var queue = new SimpleQueue(dataSource);

		queue.publish("myqueue", "well that was easy");

		queue.subscribe("myqueue", x -> {
			System.out.println(x.getMessage());
		});

		System.out.println("Now I am here");

		Utils.sleep(1000);

		queue.stop();
	}
}
