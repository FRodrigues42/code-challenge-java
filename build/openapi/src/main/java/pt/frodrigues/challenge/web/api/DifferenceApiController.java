package pt.frodrigues.challenge.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-08-09T02:09:27.280675+01:00[Europe/Lisbon]")
@Controller
@RequestMapping("${openapi.code-challenge-java.base-path:/FRodrigues42/CodeChallengeJava/0.0.1}")
public class DifferenceApiController implements DifferenceApi {

    private final DifferenceApiDelegate delegate;

    public DifferenceApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) DifferenceApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new DifferenceApiDelegate() {});
    }

    @Override
    public DifferenceApiDelegate getDelegate() {
        return delegate;
    }

}
