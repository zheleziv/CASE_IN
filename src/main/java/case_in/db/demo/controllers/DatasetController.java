package case_in.db.demo.controllers;

import case_in.db.demo.entity.Dataset;
import case_in.db.demo.repository.DatasetRepository;
import case_in.db.demo.response.DatasetResponse;
import case_in.db.demo.response.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class DatasetController
{
    @Autowired
    DatasetRepository datasetRepository;

    @RequestMapping(value = "/getAllDatasetByIdAndMonth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DatasetResponse>  getDatasetById(String Id, int Month, Model model)
    {
        List<Dataset> datasetList = new ArrayList<>();
        java.sql.Date sqlDate = new Date(2021-1900,Month - 1,1);
        Optional<Dataset> datasetOptional;
        for(int i = 2; i <= 32; i++)
        {
            sqlDate = new Date(2021-1900,Month - 1,i - 1);
            datasetOptional = datasetRepository.findByDateAndTransportId(sqlDate, Id);
            if(datasetOptional.isPresent()) datasetList.add(datasetOptional.get());
        }

        return datasetList.stream().map(response -> new DatasetResponse(response.getTransportId(),response.getDate(),response.getMileage(),
                        response.getDrivingTime(),response.getEngineOperatingTime(),
                        response.getEngineInMotionTime(),response.getEngineWOMotionTime(),
                        response.getEngineIdlingTime(),response.getEngineNormaRpmTime(),
                        response.getEngineMaxRpmTime(),response.getEngineOffTime(),
                        response.getEngineUnderLoadTime(),response.getInitialFuelVolume(),
                        response.getFinalFuelVolume())).
                collect(Collectors.toList());
    }

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

    @RequestMapping(value = "/getAllDatasetByIdAndDateAndDate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DatasetResponse> getAllDatasetByTransportIdAndDateAndDate(String Id, String Date1, String Date2, Model model) throws ParseException
    {
        Calendar instance = Calendar.getInstance();
        List<Dataset> datasetList = new ArrayList<Dataset>();

        java.util.Date dataParsed1 = new SimpleDateFormat("dd.MM.yyyy").parse(Date1);
        java.util.Date dataParsed2 = new SimpleDateFormat("dd.MM.yyyy").parse(Date2);
        java.util.Date tmpData;

        if((dataParsed2.getTime() - dataParsed1.getTime()) < 0)
        {
            tmpData = dataParsed1;
            dataParsed1 = dataParsed2;
            dataParsed2 = tmpData;
        }
        long diff = dataParsed2.getTime() - dataParsed1.getTime();
        long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        Optional<Dataset> datasetOptional;
        for(int i = 0; i<=diffDays;i++)
        {
            instance.setTime(dataParsed1);
            instance.add(Calendar.DAY_OF_MONTH, i);
            java.util.Date newDate = instance.getTime();
            datasetOptional = datasetRepository.findByDateAndTransportId(new java.sql.Date(newDate.getTime()), Id);
            if(datasetOptional.isPresent()) datasetList.add(datasetOptional.get());
        }

        return datasetList.stream().map(response -> new DatasetResponse(response.getTransportId(),response.getDate(),response.getMileage(),
                        response.getDrivingTime(),response.getEngineOperatingTime(),
                        response.getEngineInMotionTime(),response.getEngineWOMotionTime(),
                        response.getEngineIdlingTime(),response.getEngineNormaRpmTime(),
                        response.getEngineMaxRpmTime(),response.getEngineOffTime(),
                        response.getEngineUnderLoadTime(),response.getInitialFuelVolume(),
                        response.getFinalFuelVolume())).
                collect(Collectors.toList());
    }

    @RequestMapping(value = "/getAllDate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StringResponse> getAllType(Model model)
    {
        List<Dataset> datasetsAll = new ArrayList<Dataset>();

        datasetRepository.findAll().forEach(datasetsAll::add);
        List<Date> gg = datasetsAll.stream().map(dataset -> dataset.getDate()).distinct().collect(Collectors.toList());

        return gg.stream().map(date -> new StringResponse(date.getDate()+"."+(date.getMonth()+1)+"."+(1900+date.getYear()))).distinct()
                .collect(Collectors.toList());
    }
}
