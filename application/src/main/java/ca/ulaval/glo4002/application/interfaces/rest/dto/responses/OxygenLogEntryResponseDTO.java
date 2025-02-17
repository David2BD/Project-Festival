package ca.ulaval.glo4002.application.interfaces.rest.dto.responses;

public class OxygenLogEntryResponseDTO {
    public String type;
    public String grade;
    public int quantity;
    public String timestamp;

    public OxygenLogEntryResponseDTO(String type, String grade, int quantity, String timestamp) {
        this.type = type;
        this.grade = grade;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

}