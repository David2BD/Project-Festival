package ca.ulaval.glo4002.application.domain.transport.transportable;

import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import ca.ulaval.glo4002.application.domain.transport.types.PassengerType;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransportableGroup extends Transportable {
    int groupSize;
    List<PassNumber> passNumbers;

    public TransportableGroup(LocalDate dateArrival, LocalDate dateDeparture, Artist artist) {
        super(dateArrival, dateDeparture);
        this.groupSize = artist.getGroupPeopleCount();
        passNumbers = generateIDsForArtistMembers(artist);
    }

    private List<PassNumber> generateIDsForArtistMembers(Artist artist) {
        List<PassNumber> ids = new ArrayList<>();
        for (int i = 1; i <= groupSize; i++) {
            ids.add(new PassNumber(artist.generatePassString(i)));
        }
        return ids;
    }

    public ShuttleType getShuttleType() {
        if (groupSize == 1) {
            return ShuttleType.ET_SPACESHIP;
        }
        else if (groupSize > 1) {
            return ShuttleType.MILLENNIUM_FALCON;
        }
        return null;
    }

    public List<PassNumber> getPassNumbers() {
        return passNumbers;
    }

    public PassengerType getPassengerType() {
        return PassengerType.ARTIST;
    }
}
