package data;

import domain.modelo.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamionesRepository extends JpaRepository<Camion, Integer> {
}
