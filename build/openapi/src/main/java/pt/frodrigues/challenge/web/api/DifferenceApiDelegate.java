package pt.frodrigues.challenge.web.api;

import pt.frodrigues.challenge.service.api.dto.Difference;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link DifferenceApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-08-09T02:09:27.280675+01:00[Europe/Lisbon]")
public interface DifferenceApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /difference : Get the difference between the sum of the squares of the N natural numbers and the square of the sum of the same numbers 
     *
     * @param number Number for the first N natural numbers (required)
     * @return Successful calculation (status code 200)
     *         or Invalid number (must be greater than 0 and less or equal to 100) (status code 400)
     * @see DifferenceApi#getDifference
     */
    default ResponseEntity<Difference> getDifference(Long number) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"occurrences\" : 1, \"number\" : 6, \"datetime\" : \"2000-01-23T04:56:07.000+00:00\", \"value\" : 0 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
