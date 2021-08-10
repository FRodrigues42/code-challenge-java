package pt.frodrigues.challenge.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import pt.frodrigues.challenge.domain.Difference;

import java.util.Optional;

/**
 * Spring Data SQL repository for the Difference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DifferenceRepository extends JpaRepository<Difference, Long> {
    Optional<Difference> findByNumber(Long n);
}
