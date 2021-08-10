package pt.frodrigues.challenge.web.rest.vm;

import java.time.ZonedDateTime;
import pt.frodrigues.challenge.domain.Difference;

/**
 * View model to comply with exercise requirements
 */
public class DifferenceVM {

    private ZonedDateTime datetime;
    private Long value;
    private Long number;
    private Long occurrences;

    public DifferenceVM() {}

    public DifferenceVM(Difference diff) {
        datetime = diff.getDatetime();
        value = diff.getValue();
        number = diff.getNumber();
        occurrences = diff.getOccurrences();
    }

    public ZonedDateTime getDatetime() {
        return datetime;
    }

    public Long getValue() {
        return value;
    }

    public Long getNumber() {
        return number;
    }

    public Long getOccurrences() {
        return occurrences;
    }
}
