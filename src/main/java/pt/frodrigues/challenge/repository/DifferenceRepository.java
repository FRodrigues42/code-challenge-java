package pt.frodrigues.challenge.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import pt.frodrigues.challenge.domain.Difference;

/**
 * Spring Data SQL repository for the Difference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DifferenceRepository extends JpaRepository<Difference, Long> {}
