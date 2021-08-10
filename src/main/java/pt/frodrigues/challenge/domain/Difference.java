package pt.frodrigues.challenge.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Difference.
 */
@Entity
@Table(name = "difference")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Difference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "datetime")
    private Instant datetime;

    @NotNull
    @Column(name = "value", nullable = false)
    private Long value;

    @NotNull
    @Min(value = 1L)
    @Max(value = 100L)
    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @Column(name = "occurrences")
    private Long occurrences;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Difference id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDatetime() {
        return this.datetime;
    }

    public Difference datetime(Instant datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public Long getValue() {
        return this.value;
    }

    public Difference value(Long value) {
        this.value = value;
        return this;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getNumber() {
        return this.number;
    }

    public Difference number(Long number) {
        this.number = number;
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getOccurrences() {
        return this.occurrences;
    }

    public Difference occurrences(Long occurrences) {
        this.occurrences = occurrences;
        return this;
    }

    public void setOccurrences(Long occurrences) {
        this.occurrences = occurrences;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Difference)) {
            return false;
        }
        return id != null && id.equals(((Difference) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Difference{" +
            "id=" + getId() +
            ", datetime='" + getDatetime() + "'" +
            ", value=" + getValue() +
            ", number=" + getNumber() +
            ", occurrences=" + getOccurrences() +
            "}";
    }
}
