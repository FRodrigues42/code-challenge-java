package pt.frodrigues.challenge.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InvalidNumberException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public InvalidNumberException() {
        super(ErrorConstants.INVALID_NUMBER, "Invalid number", Status.BAD_REQUEST);
    }
}
