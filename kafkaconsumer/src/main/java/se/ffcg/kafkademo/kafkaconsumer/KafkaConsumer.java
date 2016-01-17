package se.ffcg.kafkademo.kafkaconsumer;

import org.apache.kafka.common.serialization.StringDeserializer;
import se.ffcg.kafkademo.kafkaconsumer.processing.Poller;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaConsumer {

  private String servers = "localhost:9092";
  private String topic = "game.events";

  public static void main(String[] args) {
    System.out.println("Startar consumer");
    new KafkaConsumer().poller();
  }

  public Poller poller() {
    Properties conf = new Properties();
    conf.put("bootstrap.servers", servers);
    conf.put("group.id", "group01");
    conf.put("key.deserializer", StringDeserializer.class);
    conf.put("value.deserializer", StringDeserializer.class);
    Poller poller = new Poller(conf, Collections.singletonList(topic));

    ExecutorService es = Executors.newFixedThreadPool(1);
    es.execute(poller);

    return poller;
  }


}
