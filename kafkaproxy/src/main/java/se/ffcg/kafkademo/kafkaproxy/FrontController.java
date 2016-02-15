package se.ffcg.kafkademo.kafkaproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import se.ffcg.kafkademo.kafkaproxy.game.GameEngine;
import se.ffcg.kafkademo.kafkaproxy.game.GameOutput;
import se.ffcg.kafkademo.kafkaproxy.model.PollEvent;
import se.ffcg.kafkademo.kafkaproxy.processing.ProcessingTask;
import se.ffcg.kafkademo.kafkaproxy.game.GameInput;

import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class FrontController {

  private static Logger log = LoggerFactory.getLogger(FrontController.class);

  private static final String GAME_ID_PREFIX = UUID.randomUUID().toString();

  private static final long PROCESSING_TIME = 100;

  private final AtomicLong counter = new AtomicLong();

  @Autowired
  private GameEngine engine;
  // private final GameEngine engine = new LocalGameEngine();

  private final Timer timer = new Timer();

  //    @RequestMapping("/")
  //    public String index() {
  //
  //        return "This is the casino front!";
  //    }

  @RequestMapping("/publish")
  public String publish(
      @RequestParam(required = true, value = "param1") String param1,
      @RequestParam(required = true, value = "param2") String param2
  ) {
    log.info("p1: {}, p2 {}", param1, param2);

    playGameBlocking("client1", Integer.parseInt(param1), Integer.parseInt(param2));
    return "Tack för din medverkan";
  }

  @RequestMapping("/playgameBlocking")
  public PollEvent playGameBlocking(
      @RequestParam(required = true, value = "clientId") String clientId,
      @RequestParam(required = true, value = "playedNumber") Integer playedNumber,
      @RequestParam(required = true, value = "playedAmount") Integer playedAmount
  ) {


    Assert.isTrue(playedNumber >= 0 && playedNumber <= 36);
    GameInput gameInput = new GameInput(clientId, playedNumber, playedAmount);
    PollEvent pollEvent = new PollEvent("ID: "+counter.incrementAndGet()
    , playedNumber, playedAmount);
    String gameId = String.format("%s-%d", GAME_ID_PREFIX, counter.incrementAndGet());

    // try {
    // Thread.sleep(PROCESSING_TIME);
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }

    GameOutput go = engine.doPlay(pollEvent);

    return null;
  }

  @RequestMapping("/playgame")
  public DeferredResult<PollEvent> playGame(
      @RequestParam(required = true, value = "clientId") String clientId,
      @RequestParam(required = true, value = "playedNumber") Integer playedNumber,
      @RequestParam(required = true, value = "playedAmount") Integer playedAmount
  ) {

    Assert.isTrue(playedNumber >= 0 && playedNumber <= 36);
    GameInput gameInput = new GameInput(clientId, playedNumber, playedAmount);
    String gameId = String.format("%s-%d", GAME_ID_PREFIX, counter.incrementAndGet());

    DeferredResult<PollEvent> deferredResult = new DeferredResult<PollEvent>();
    ProcessingTask task = new ProcessingTask(deferredResult, engine, gameInput, gameId);
    timer.schedule(task, PROCESSING_TIME);

    return deferredResult;

  }



}
