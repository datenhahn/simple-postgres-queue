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
    var queue = new SimpleQueue(dataSource);

    queue.publish("myqueue", "well that was easy");

    queue.subscribe("myqueue", x -> {
        System.out.println(x.getMessage());
    });
```

## Subscriber Callback

The subscriber callback has access to the message properties:

    id
    queue
    traceId
    created
    updated
    publisherId
    subscriberId
    status
    statusText
    message

The subscriber and publisher ID can be set as `hostId`in the constructor.

    public SimpleQueue(DataSource ds, String hostId, boolean notifyProcessing, String tableName)
    
If parameter `notifyProcessing` is set to true it will update the table to state "PROCESSING" when starting
to process the callback. If set to false it will only update after finishing the callback. 
