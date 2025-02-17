package ca.ulaval.glo4002.application.infrastructure.inMemory;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.festival.FestivalRepository;

import java.util.ArrayList;
import java.util.List;

public class FestivalRepositoryInMemory implements FestivalRepository {
    private final List<Festival> festivals;

    public FestivalRepositoryInMemory() {
        festivals = new ArrayList<>();
    }

    @Override
    public Festival getFestival() {
        return festivals.isEmpty() ? null : festivals.get(0);
    }

    @Override
    public void saveFestival(Festival festival) {
        Festival currentFestival = null;
        for (Festival festivalIteration : festivals) {
            if (festivalIteration.getId().equals(festival.getId())) currentFestival = festivalIteration;
        }
        if (currentFestival == null) {
            festivals.add(festival);
        }
        else {
            festivals.remove(currentFestival);
            festivals.add(festival);
        }
    }

    public void addFestival(Festival defaultFestival) {
        festivals.add(defaultFestival);
    }
}
