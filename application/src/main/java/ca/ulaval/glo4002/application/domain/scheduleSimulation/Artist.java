package ca.ulaval.glo4002.application.domain.scheduleSimulation;

import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.festival.FestivalDates;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.application.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.application.domain.transport.types.ShuttleType;

import java.math.BigDecimal;

public class Artist extends OxygenRequester {
    private final int DAILY_OXYGEN_USE_PER_PERSON = 6;
    private String name;
    private final MoneyAmount cost;
    private int popularity;
    private int groupPeopleCount;
    private String musicalGenre;
    private ShuttleType shuttleType;

    public Artist(
            String name, MoneyAmount cost, int popularity, int groupSize, String musicalGenre
    ) {
        this.name = name;
        this.cost = cost;
        this.popularity = popularity;
        this.groupPeopleCount = groupSize;
        this.musicalGenre = musicalGenre;
        setShuttleType();
    }

    public Artist(String artistName, MoneyAmount cost) {
        this.name = artistName;
        this.cost = cost;
    }

    private void setShuttleType() {
        if (this.groupPeopleCount == 1) {
            this.shuttleType = ShuttleType.ET_SPACESHIP;
        }
        else if (this.groupPeopleCount > 1) {
            this.shuttleType = ShuttleType.MILLENNIUM_FALCON;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String generatePassString(int groupPeopleNumber) {
        return this.name + "-" + String.format("%03d", groupPeopleNumber);
    }

    public void setShuttleType(ShuttleType shuttleType) {
        this.shuttleType = shuttleType;
    }

    public MoneyAmount getCost() {
        return cost;
    }

    public String getMusicalGenre() {
        return musicalGenre;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getGroupPeopleCount() {
        return groupPeopleCount;
    }

    public BigDecimal getCostValue() {
        return cost.getAmount();
    }

    public MoneyAmount subtractFromBudgetIfPossible(MoneyAmount headlinerBudget) {
        if (headlinerBudget.isGreaterOrEqualTo(cost)) {
            return headlinerBudget.subtract(cost);

        }
        return headlinerBudget;
    }

    public ShuttleType getShuttleType() {
        return this.shuttleType;
    }

    @Override
    public OxygenGrade getBaseOxygenGrade() {
        return OxygenGrade.B;
    }

    @Override
    public int calculateTanksNeeded(FestivalDates festivalDates) {
        return groupPeopleCount * DAILY_OXYGEN_USE_PER_PERSON;
    }
}
