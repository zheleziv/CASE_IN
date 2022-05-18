package case_in.db.demo.controllers;

import case_in.db.demo.entity.Vehicles;
import case_in.db.demo.repository.VehiclesRepository;
import case_in.db.demo.response.StringResponse;
import case_in.db.demo.response.VehiclesResponse;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class VehiclesController
{
    @Autowired
    VehiclesRepository vehiclesRepository;

    @RequestMapping(value = "/vehicles/add", method = RequestMethod.POST)
    public HttpStatus addVehicles(@RequestParam(required = false) String Name,
                                  String Id, String transportNumber,
                                  String transportType, Model model)
    {
        if(!vehiclesRepository.findById(Id).isPresent())
        {
            Vehicles vehicles = new Vehicles();
            vehicles.setTransportName(Name);
            vehicles.setId(Id);
            vehicles.setTransportNumber(transportNumber);
            vehicles.setTransportType(transportType);

            vehiclesRepository.save(vehicles);
            return HttpStatus.ACCEPTED;
        }
        else
        {
            return HttpStatus.FORBIDDEN;
        }
    }

    @RequestMapping(value = "/getAllVehicles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehiclesResponse> getAllVehicles(Model model)
    {
        List<Vehicles> vehiclesAll = new ArrayList<Vehicles>();

        vehiclesRepository.findAll().forEach(vehiclesAll::add);

        return vehiclesAll.stream().map(vehicles -> new VehiclesResponse(vehicles.getId(), vehicles.getTransportName(),
                        vehicles.getTransportNumber(), vehicles.getTransportType())).
                collect(Collectors.toList());
    }


    @RequestMapping(value = "/getVehiclesById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public VehiclesResponse getVehicleById(String Id, Model model)
    {
        Optional<Vehicles> venOptional = vehiclesRepository.findById(Id);
        if (!venOptional.isPresent()) return null;
        Vehicles response = venOptional.get();
        return new VehiclesResponse(response.getId(), response.getTransportName(),
                                    response.getTransportNumber(), response.getTransportType());
    }

    @RequestMapping(value = "/getAllVehiclesByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehiclesResponse> getAllVehicleByName(String Name, Model model)
    {
        List<Vehicles> vehiclesAll = new ArrayList<Vehicles>();

        vehiclesRepository.findAllByTransportName(Name).forEach(vehiclesAll::add);

        return vehiclesAll.stream().map(vehicles -> new VehiclesResponse(vehicles.getId(), vehicles.getTransportName(),
                        vehicles.getTransportNumber(), vehicles.getTransportType())).
                collect(Collectors.toList());
    }

    @RequestMapping(value = "/getAllVehiclesByType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VehiclesResponse> getAllVehicleByType(String Type, Model model)
    {
        List<Vehicles> vehiclesAll = new ArrayList<Vehicles>();

        vehiclesRepository.findAllByTransportType(Type).forEach(vehiclesAll::add);

        return vehiclesAll.stream().map(vehicles -> new VehiclesResponse(vehicles.getId(), vehicles.getTransportName(),
                        vehicles.getTransportNumber(), vehicles.getTransportType())).
                collect(Collectors.toList());
    }

    @RequestMapping(value = "/getAllType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StringResponse> getAllType(Model model)
    {
        List<Vehicles> vehiclesAll = new ArrayList<Vehicles>();

        vehiclesRepository.findAll().forEach(vehiclesAll::add);
        List<String> gg = vehiclesAll.stream().map(vehicles -> vehicles.getTransportType()).distinct().collect(Collectors.toList());

        return gg.stream().map(vehicles -> new StringResponse(vehicles)).distinct()
                .collect(Collectors.toList());
    }


}
