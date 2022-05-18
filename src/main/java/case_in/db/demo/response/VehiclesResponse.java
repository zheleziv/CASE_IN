package case_in.db.demo.response;

import case_in.db.demo.entity.Vehicles;

import java.util.List;

public class VehiclesResponse
{
    public VehiclesResponse(String Id, String TransportName, String transportNumber, String TransportType)
    {
        this.Id = Id;
        this.TransportName = TransportName;
        this.TransportNumber = transportNumber;
        this.TransportType = TransportType;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return TransportName;
    }

    public void setName(String name) {
        TransportName = name;
    }

    public String getTransportNumber() {
        return TransportNumber;
    }

    public void setTransportNumber(String transportNumber) {
        TransportNumber = transportNumber;
    }

    public String getTransportType() {
        return TransportType;
    }

    public void setTransportType(String transportType) {
        TransportType = transportType;
    }

    private String Id;
    private String TransportName;
    private String TransportNumber;
    private String TransportType;
}
