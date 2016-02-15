package se.ffcg.kafkademo.kafkaproxy.model;

public class PollEvent {

    private final String gameId;
    private final int param1;
    private final int param2;

    public PollEvent(String gameId, int param1, int param2) {
        super();
        this.gameId = gameId;
        this.param1 = param1;
        this.param2 = param2;
    }

    public int getParam1() {
        return param1;
    }

    public int getParam2() {
        return param2;
    }

    public String getGameId() {
        return gameId;
    }

    @Override public String toString() {
        return "PollEvent{" +
            "gameId='" + gameId + '\'' +
            ", param1=" + param1 +
            ", param2=" + param2 +
            '}';
    }
}
