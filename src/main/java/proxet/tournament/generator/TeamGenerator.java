package proxet.tournament.generator;

import proxet.tournament.generator.dto.Player;
import proxet.tournament.generator.dto.PlayerWithWaitingTime;
import proxet.tournament.generator.dto.TeamGeneratorResult;
import proxet.tournament.reader.WaitTimeStatReader;

import java.util.*;

public class TeamGenerator {

    public TeamGeneratorResult generateTeams(String filePath) {
        List<PlayerWithWaitingTime> playersOrder = new WaitTimeStatReader(filePath).getPlayersWaitingTime();
        Collections.sort(playersOrder);

        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();
        boolean isTeam1Full = fillTeam(team1, playersOrder);
        boolean isTeam2Full = fillTeam(team2, playersOrder);

        if(!isTeam1Full || !isTeam2Full) {
            return null;
        }

        return new TeamGeneratorResult(team1, team2);
    }

    /**
     * This method fills given team from playersOrder.
     * @param team given team which should be filled.
     * @param playersOrder current order of waiting players.
     * @return Ture if team was successfully filled. False if there is not enough needed players in playersOrder.
     */
    private boolean fillTeam(List<Player> team, List<PlayerWithWaitingTime> playersOrder) {
        List<PlayerWithWaitingTime> playersToBeRemoved = new ArrayList<>();
        int teamPlayersAmount = 9;
        int participantAmount = 0;
        for (PlayerWithWaitingTime playerWithWaitingTime : playersOrder) {
            // not enough players in order to start a match.
            if(Objects.isNull(playerWithWaitingTime)) {
                return false;
            }

            Player player = playerWithWaitingTime.getPlayer();
            if (isVehicleSeatAvailable(team, player.getVehicleType())) {
                team.add(player);
                participantAmount++;
                playersToBeRemoved.add(playerWithWaitingTime);
            }
            if(participantAmount == teamPlayersAmount) {
                break;
            }
        }

        playersOrder.removeAll(playersToBeRemoved);
        return true;
    }

    /**
     * Checking is there any free seats available of given vehicleType in given team.
     * @return true if there is at least one available seat. false if it is not.
     */
    private boolean isVehicleSeatAvailable(List<Player> team, int vehicleType) {
        int availableVehicleSeats = 3;
        return team.stream().filter(p -> p.getVehicleType() == vehicleType).count() < availableVehicleSeats;
    }


}
