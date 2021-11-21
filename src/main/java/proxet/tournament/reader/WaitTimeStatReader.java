package proxet.tournament.reader;

import proxet.tournament.generator.dto.Player;
import proxet.tournament.generator.dto.PlayerWithWaitingTime;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class WaitTimeStatReader {
    private final File file;

    public WaitTimeStatReader(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * This method is a wrap of parseFile() to avoid exceptions handling in logical code part
     *
     * @return parsed information about users.
     */
    public List<PlayerWithWaitingTime> getPlayersWaitingTime() {
        try {
            return parseFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * This method reads file and parse it to a List of java Objects
     *
     * @return parsed information about users.
     */
    private List<PlayerWithWaitingTime> parseFile() throws IOException {
        List<PlayerWithWaitingTime> resultPlayers = new ArrayList<>();
        Reader reader = new FileReader(file);
        StringBuilder builder = new StringBuilder();

        int i = reader.read();
        while (i >= 0) {
            if ((char) i == '\n') {
                resultPlayers.add(getRecord(builder.toString()));
                builder = new StringBuilder();
            } else {
                builder.append((char) i);
            }
            i = reader.read();
        }
        //write last record.
        resultPlayers.add(getRecord(builder.toString()));
        return resultPlayers;
    }

    /**
     * This method parse raw user records into PlayerWithWaitingTime
     *
     * @param rawRecord - simple raw record about user info. Example: "[#AndreO#	92	2"
     * @return parsed user info record.
     */
    private PlayerWithWaitingTime getRecord(String rawRecord) {
        String[] splitEntry = rawRecord.split("\t");
        String playerName = splitEntry[0];
        int playerWaitingTime = Integer.parseInt(splitEntry[1]);
        int playerVehicle = Integer.parseInt(splitEntry[2]);

        return new PlayerWithWaitingTime(
                new Player(playerName, playerVehicle),
                playerWaitingTime
        );
    }
}