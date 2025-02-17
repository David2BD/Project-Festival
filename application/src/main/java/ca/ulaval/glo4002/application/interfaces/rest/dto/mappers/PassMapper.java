package ca.ulaval.glo4002.application.interfaces.rest.dto.mappers;

import ca.ulaval.glo4002.application.domain.festival.Festival;
import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.pass.PassFactory;
import ca.ulaval.glo4002.application.interfaces.rest.dto.requests.PassRequestDTO;
import ca.ulaval.glo4002.application.interfaces.rest.dto.responses.PassResponseDTO;
import ca.ulaval.glo4002.application.utils.DateUtils;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PassMapper {

    private final PassFactory passFactory;
    private final Festival festival;

    @Inject
    public PassMapper(PassFactory passFactory, Festival festival) {
        this.passFactory = passFactory;
        this.festival = festival;
    }

    public List<Pass> toPasses(List<PassRequestDTO> passRequestDTOS) {
        List<Pass> passes = new ArrayList<>();
        for (PassRequestDTO passRequestDTO : passRequestDTOS) {
            passes.add(toPass(passRequestDTO));
        }
        return passes;
    }

    public List<PassResponseDTO> toPassResponseDTOs(List<Pass> passes) {
        return passes.stream().map(this::toPassResponseDTO).collect(Collectors.toList());
    }

    private Pass toPass(PassRequestDTO passRequestDTO) {
        return passFactory.createPass(passRequestDTO.passCategory(), passRequestDTO.passOption(),
                                      passRequestDTO.eventDate());
    }

    private PassResponseDTO toPassResponseDTO(Pass pass) {
        List<String> eventDates =
                pass.getEventDates(festival.getFestivalStartDate(), festival.getFestivalEndDate()).stream()
                        .map(DateUtils::formatLocalDate).collect(Collectors.toList());

        return new PassResponseDTO(pass.getPassNumber().getNumber(), pass.getPassCategory().toString(),
                                   pass.getPassOption().toString(), eventDates);
    }
}
