package proxet.tournament.generator.dto;

public class Player {

    private final String nickname;
    private final int vehicleType;

    public Player(String nickname, int vehicleType) {
        this.nickname = nickname;
        this.vehicleType = vehicleType;
    }

    public String getNickname() {
        return nickname;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", vehicleType=" + vehicleType +
                '}';
    }
}
