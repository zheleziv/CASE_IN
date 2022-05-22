package case_in.db.demo.repository;

import case_in.db.demo.entity.Analise;
import case_in.db.demo.entity.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnaliseRepository extends JpaRepository<Analise, String> {
    List<Analise> findAll();
    Optional<Analise> findByIdAndMonth(String Id, Integer month);
    List<Analise> findAllByTransportId(String Id);
}
