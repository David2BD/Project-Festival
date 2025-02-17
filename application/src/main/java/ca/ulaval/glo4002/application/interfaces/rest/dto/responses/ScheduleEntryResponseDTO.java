package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

public class ScheduleEntryResponseDTO {
    private final String date;
    private final String artist;

    public ScheduleEntryResponseDTO(String date, String artist) {
        this.date = date;
        this.artist = artist;
    }

    public String getDate() {
        return date;
    }

    public String getArtist() {
        return artist;
    }
}
