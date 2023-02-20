package data;

import domain.modelo.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConductoresRepository extends JpaRepository<Conductor, Integer> {
}
