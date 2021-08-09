package pt.frodrigues.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.frodrigues.challenge.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
