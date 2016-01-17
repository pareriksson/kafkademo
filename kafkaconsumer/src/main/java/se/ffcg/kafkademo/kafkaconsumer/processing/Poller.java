package se.ffcg.kafkademo.kafkaconsumer.processing;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class Poller implements Runnable {
  private final KafkaConsumer<String, String> consumer;
  private final List<String> topics;
  private final AtomicBoolean shutdown;
  private final CountDownLatch shutdownLatch;


  public Poller(Properties config, List<String> topics) {
    this.consumer = new KafkaConsumer<>(config);
    this.topics = topics;
    this.shutdown = new AtomicBoolean(false);
    this.shutdownLatch = new CountDownLatch(1);
  }

  public void run() {
    System.out.println("Poller started");
    try {
      System.out.print("Subscribing to topics "+topics+"...");
      consumer.subscribe(topics);
      System.out.println("done!");

      while (!shutdown.get()) {
        Thread.currentThread().sleep(1000);
        ConsumerRecords<String, String> records = consumer.poll(0);
        System.out.println(String.format("%d events togs emot denna sekund", records.count()));
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      consumer.close();
      shutdownLatch.countDown();
    }
  }
}
