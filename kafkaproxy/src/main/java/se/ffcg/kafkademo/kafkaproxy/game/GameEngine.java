package se.ffcg.kafkademo.kafkaproxy.game;

import se.ffcg.kafkademo.kafkaproxy.model.PollEvent;

public interface GameEngine {

    GameOutput doPlay(PollEvent input);

}
