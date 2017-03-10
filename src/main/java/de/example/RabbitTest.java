package de.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * Created by korovin on 3/10/2017.
 */
public class RabbitTest {
    private ConnectionFactory factory = new ConnectionFactory();
    private Connection connection;
    private Channel channel;
    private static final String QUEUE_NAME = "test";

    public RabbitTest() {
        // in order to create connection need to specify credentials, address
        // set connection info
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        // TCP connection
        // create the connection
        try {
            this.connection = factory.newConnection();

            // can have multiple channels from one connection
            // create the channel
            this.channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void createMessages() throws IOException {
        // publish 10 messages to queue
        String queueName = "test";
        for (int i = 1; i <= 10; i++) {
            String message = "Hello world, message #" + i;
            // in order to publish a message, use basicPublish method
            // specified empty exchange, it is mapped to default exchange
            channel.basicPublish("", queueName, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } // 10 messages are ready for consuming. they stay in the queue, until they are consumed
    }

    public void start() {
        try {
            // not event based. can query next message and it will block if no message available
            QueueingConsumer consumer = new QueueingConsumer(channel);
            // attach to channel
            channel.basicConsume(QUEUE_NAME, consumer);

            boolean removeAllUpTo = false;
            // infinite loop
            while (true) {
                // passing timeout parameter 5 sec, get message
                QueueingConsumer.Delivery delivery = consumer.nextDelivery(5000);
                // (because if there are no messages, break from program
                if (delivery == null) break;
                long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                process(delivery);
                channel.basicAck(deliveryTag, removeAllUpTo);
            }

            // close all channels and connections
            // first close channels
            channel.close();
            // then close connections
            connection.close();
        } catch (IOException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static boolean process(QueueingConsumer.Delivery delivery) throws UnsupportedEncodingException {
        String msg = new String(delivery.getBody(), "UTF-8");
        System.out.println("[x] Recv: redeliver=" + delivery.getEnvelope().isRedeliver() + " , msg=" + msg);
        return true;
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitTest daemon = new RabbitTest();
        daemon.createMessages();
        daemon.start();
    }
}
