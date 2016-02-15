package se.ffcg.kafkademo.kafkaproxy.processing;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

import se.ffcg.kafkademo.kafkaproxy.game.GameEngine;
import se.ffcg.kafkademo.kafkaproxy.game.GameOutput;
import se.ffcg.kafkademo.kafkaproxy.game.GameInput;
import se.ffcg.kafkademo.kafkaproxy.model.PollEvent;

public class ProcessingTask extends TimerTask {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessingTask.class);
    private DeferredResult<PollEvent> deferredResult;
    private GameEngine engine;
    private GameInput input;
    private String gameId;

    public ProcessingTask(DeferredResult<PollEvent> deferredResult, GameEngine engine, GameInput gameInput,
                    String gameId) {
        this.deferredResult = deferredResult;
        this.engine = engine;
        this.input = gameInput;
        this.gameId = gameId;
    }

    @Override
    public void run() {
//        if (deferredResult.isSetOrExpired()) {
//            LOG.warn("Processing of non-blocking request # already expired");
//        } else {
//            GameOutput go = engine.doPlay(input);
//            PollEvent pollEvent = new PollEvent(gameId, go.getResultNumber(), go.getGameWinnings(),
//                            go.getJackpotWinnings(),
//                            go.getRemainingBalance());
//            boolean deferredStatus = deferredResult.setResult(pollEvent);
//            LOG.debug("Processing of non-blocking request done, deferredStatus = {}", deferredStatus);
//        }
    }
}
