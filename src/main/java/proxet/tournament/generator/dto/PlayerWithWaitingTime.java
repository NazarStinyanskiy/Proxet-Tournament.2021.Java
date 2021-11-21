package proxet.tournament.generator.dto;

public class PlayerWithWaitingTime implements Comparable<PlayerWithWaitingTime> {
    private final Player player;
    private final int waitingTime;

    public PlayerWithWaitingTime(Player player, int awaitingTime) {
        this.player = player;
        this.waitingTime = awaitingTime;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    @Override
    public int compareTo(PlayerWithWaitingTime o) {
        return o.getWaitingTime() - this.waitingTime;
    }
}
