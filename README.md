# Simple Postgres Queue

This is a simple message queue implementation based on the postgresql database and the `SELECT FOR UPDATE`
table locking.

Why another message queue implementation?

* You have a postgresql database and want to make use of the existing scaling and backup features
* The application has only moderate performance requirements, but high requirements regarding data consistency
* Debugging and admin friendlyness are keyfeatures
* Simple code which you can understand (both the queue implemenation, as well as client code)

## Usage

see `src/main/java/de/ecodia/simplequeue/examples/ExampleApp.java` for the full example.

```java

    // dataSource is some standard jdbc datasource
    var queue = new Queue(dataSource);

    queue.publish("myqueue", "well that was easy");

    queue.subscribe("myqueue", x -> {
        System.out.println(x.getMessage());
    });
```

