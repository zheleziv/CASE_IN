package case_in.db.demo.repository;

import case_in.db.demo.entity.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface DatasetRepository extends JpaRepository <Dataset, String>
{
    List<Dataset> findAllByTransportId(String Id);
    List<Dataset> findAllByDate(Date date);
    Optional<Dataset> findByDateAndTransportId(Date date, String transportId);
}
