package ca.ulaval.glo4002.application.infrastructure.sqLite;

import ca.ulaval.glo4002.application.domain.pass.Pass;
import ca.ulaval.glo4002.application.domain.pass.PassFactory;
import ca.ulaval.glo4002.application.domain.pass.PassNumber;
import ca.ulaval.glo4002.application.utils.DateUtils;

public class PassMapperSQLite {
    PassFactory passFactory;

    public PassMapperSQLite() {
        passFactory = new PassFactory();
    }

    public Pass convertFromSQLite(PassSQLite passSQLite) {
        PassNumber passNumber = new PassNumber(passSQLite.passNumber());

        return passFactory.createPassWithNumber(passSQLite.passCategory(), passSQLite.passOption(),
                                                passSQLite.eventDate(), passNumber);
    }

    public PassSQLite convertToSQLite(Pass pass) {
        String passOption = pass.getPassOption().toString();
        String passCategory = pass.getPassCategory().toString();
        String passNumber = pass.getPassNumberValue();
        String eventDate = (pass.getEventDate() != null) ? DateUtils.formatLocalDate(pass.getEventDate()) : null;

        return new PassSQLite(passOption, passCategory, eventDate, passNumber);
    }
}
