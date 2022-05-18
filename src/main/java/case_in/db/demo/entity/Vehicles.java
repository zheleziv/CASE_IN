package case_in.db.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Vehicles", schema = "catalog")
public class Vehicles
{
    @Id
    @Column(name="id")
    private String id;
    @Column(name="transport_name")
    private String transportName;
    @Column(name="transport_number")
    private String transportNumber;
    @Column(name="transport_type")
    private String transportType;

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getTransportNumber() {
        return transportNumber;
    }

    public void setTransportNumber(String transportNumber) {
        this.transportNumber = transportNumber;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String TransportName) {
        this.transportName = TransportName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


}
