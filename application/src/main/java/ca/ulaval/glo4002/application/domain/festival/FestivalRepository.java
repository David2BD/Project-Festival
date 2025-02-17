package ca.ulaval.glo4002.application.domain.festival;

public interface FestivalRepository {
    Festival getFestival();

    void saveFestival(Festival festival);

    void addFestival(Festival defaultFestival);
}
