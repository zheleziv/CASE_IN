package case_in.db.demo.repository;

import case_in.db.demo.entity.Vehicles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiclesRepository extends JpaRepository<Vehicles, String>
{
    List<Vehicles> findAllByTransportName(String TransportName);
    List<Vehicles> findAllByTransportType(String TransportType);
    List<Vehicles> findAllByTransportNameAndAndTransportType(String TransportName,String TransportType);
    List<Vehicles> findAll();
    Optional<Vehicles> findById(String Id);
}
