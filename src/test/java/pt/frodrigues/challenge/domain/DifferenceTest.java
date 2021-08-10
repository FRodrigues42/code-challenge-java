package pt.frodrigues.challenge.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pt.frodrigues.challenge.web.rest.TestUtil;

class DifferenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Difference.class);
        Difference difference1 = new Difference();
        difference1.setId(1L);
        Difference difference2 = new Difference();
        difference2.setId(difference1.getId());
        assertThat(difference1).isEqualTo(difference2);
        difference2.setId(2L);
        assertThat(difference1).isNotEqualTo(difference2);
        difference1.setId(null);
        assertThat(difference1).isNotEqualTo(difference2);
    }
}
