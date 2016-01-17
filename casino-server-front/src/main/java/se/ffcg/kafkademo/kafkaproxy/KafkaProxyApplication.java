package se.ffcg.kafkademo.kafkaproxy;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import se.ffcg.kafkademo.kafkaproxy.game.GameEngine;
import se.ffcg.kafkademo.kafkaproxy.game.KafkaGameEngine;
import se.ffcg.kafkademo.kafkaproxy.processing.Poller;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class KafkaProxyApplication {

  private static final Logger LOG = LoggerFactory.getLogger(KafkaProxyApplication.class);

  @Value("${kafka.game.topic}")
  private String topic;

  @Value("${kafka.bootstrap.servers}")
  private String servers;

  public KafkaProxyApplication() {
    LOG.info("Starting KafkaProxyApplication...");

  }

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(KafkaProxyApplication.class, args);

    // System.out.println("Let's inspect the beans provided by Spring Boot:");
    //
    //
    // String[] beanNames = ctx.getBeanDefinitionNames();
    // Arrays.sort(beanNames);
    // for (String beanName : beanNames) {
    // System.out.println(beanName);
    // }
  }

  @Bean
  public Poller poller() {
    Properties conf = new Properties();
    conf.put("bootstrap.servers", servers);
    conf.put("group.id", "group01");
    conf.put("key.deserializer", StringDeserializer.class);
    conf.put("value.deserializer", StringDeserializer.class);
    Poller poller = new Poller(conf, Collections.singletonList(topic));

    ExecutorService es = Executors.newFixedThreadPool(2);
    es.execute(poller);

    return poller;
  }

  @Bean
  public GameEngine getGameEngine() {
    return new KafkaGameEngine();
//    return new LocalGameEngine();

  }

}
