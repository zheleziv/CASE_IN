package case_in.db.demo.controllers;

import case_in.db.demo.entity.Dataset;
import case_in.db.demo.entity.Vehicles;
import case_in.db.demo.repository.DatasetRepository;
import case_in.db.demo.repository.VehiclesRepository;
import case_in.db.demo.response.DatasetResponse;
import case_in.db.demo.response.VehiclesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class DatasetController
{
    @Autowired
    DatasetRepository datasetRepository;

//    @RequestMapping(value = "/getDatasetById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public DatasetResponse getDatasetById(String Id, Model model)
//    {
//        Optional<Dataset> datasetOptional = datasetRepository.findAllByTransportId(Id);
//        if (!datasetOptional.isPresent()) return null;
//        Dataset response = datasetOptional.get();
//        return new DatasetResponse(response.getTransportId(),response.getDate(),response.getMileage(),
//                response.getDrivingTime(),response.getEngineOperatingTime(),
//                response.getEngineInMotionTime(),response.getEngineWOMotionTime(),
//                response.getEngineIdlingTime(),response.getEngineNormaRpmTime(),
//                response.getEngineMaxRpmTime(),response.getEngineOffTime(),
//                response.getEngineUnderLoadTime(),response.getInitialFuelVolume(),
//                response.getFinalFuelVolume());
//    }

    @RequestMapping(value = "/getAllDatasetById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DatasetResponse> getAllDatasetByTransportId(String Id, Model model)
    {
        List<Dataset> datasetsAll = new ArrayList<Dataset>();

        datasetRepository.findAllByTransportId(Id).forEach(datasetsAll::add);

        return datasetsAll.stream().map(response -> new DatasetResponse(response.getTransportId(),response.getDate(),response.getMileage(),
                        response.getDrivingTime(),response.getEngineOperatingTime(),
                        response.getEngineInMotionTime(),response.getEngineWOMotionTime(),
                        response.getEngineIdlingTime(),response.getEngineNormaRpmTime(),
                        response.getEngineMaxRpmTime(),response.getEngineOffTime(),
                        response.getEngineUnderLoadTime(),response.getInitialFuelVolume(),
                        response.getFinalFuelVolume())).
                collect(Collectors.toList());
    }
}
