package ca.ulaval.glo4002.application.interfaces.rest.dto.requests;

import java.util.List;

public record OrderRequestDTO(String orderDate, String vendorCode, List<PassRequestDTO> passes) {}
