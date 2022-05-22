package case_in.db.demo.controllers;

import case_in.db.demo.entity.Analise;
import case_in.db.demo.entity.Dataset;
import case_in.db.demo.entity.Vehicles;
import case_in.db.demo.repository.AnaliseRepository;
import case_in.db.demo.repository.DatasetRepository;
import case_in.db.demo.repository.VehiclesRepository;
import case_in.db.demo.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import java.sql.Date;
import java.sql.Time;
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
    @Autowired
    VehiclesRepository vehiclesRepository;
    @Autowired
    AnaliseRepository analiseRepository;

    @RequestMapping(value = "/getAllDatasetByIdAndMonth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DatasetResponse>  getDatasetById(String Id, int Month, Model model)
    {
        List<Dataset> datasetList = new ArrayList<>();
        java.sql.Date sqlDate = new Date(2021-1900,Month - 1,1);
        java.sql.Date sqlDateEnd = new Date(2021-1900,Month,1);
        Optional<Dataset> datasetOptional;
        for(int i = 2; i <= 32; i++)
        {
            sqlDate = new Date(2021-1900,Month - 1,i - 1);
            if(sqlDate.equals(sqlDateEnd)) break;
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
    @RequestMapping(value = "/getAnaliseByIdAndDateAndDate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnaliseResponse> getAnaliseByIdAndDateAndDate(String Id, String Date1, String Date2, Model model) throws ParseException
    {
        List<AnaliseResponse> ff = new ArrayList<>();
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
        int diffDays = (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        Optional<Dataset> datasetOptional;
        long sumOperatTime = 0;
        long sumMotTime = 0;
        long sumUnderTime = 0;
        double sumStartFuel = 0;
        double sumFinalFuel = 0.0;
        double suMil = 0.0;
        for(int i = 0; i<=diffDays;i++)
        {
            instance.setTime(dataParsed1);
            instance.add(Calendar.DAY_OF_MONTH, i);
            java.util.Date newDate = instance.getTime();

            datasetOptional = datasetRepository.findByDateAndTransportId(new java.sql.Date(newDate.getTime()), Id);
            if(datasetOptional.isPresent())
            {
                sumOperatTime +=  datasetOptional.get().getEngineOperatingTime().getTime() + 25200000;
                sumMotTime    +=  datasetOptional.get().getEngineInMotionTime().getTime() + 25200000;
                sumUnderTime  +=  datasetOptional.get().getEngineUnderLoadTime().getTime() + 25200000;
                sumStartFuel  += datasetOptional.get().getInitialFuelVolume();
                sumFinalFuel  += datasetOptional.get().getFinalFuelVolume();
                suMil += datasetOptional.get().getMileage();
            }
        }
        ff.add(new AnaliseResponse(sumOperatTime / 1000, sumMotTime / 1000, sumUnderTime / 1000,sumStartFuel, sumFinalFuel, suMil, diffDays + 1));
        return ff;
    }

    @RequestMapping(value = "/makeCalendarYear", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus getCalendarYear(Model model) throws ParseException
    {
        List<Vehicles> vehicles = vehiclesRepository.findAll();
        List<String> ids = new ArrayList<>();
        vehicles.forEach(e ->ids.add(e.getId()));
        Optional<Analise> analiseOptional;
        Optional<Dataset> datasetOptional;

        for(String e : ids)
        {

            for(int j = 0; j < 12; j++)
            {

                double mileage = 0;
                long drivingTime = 0;
                long engineOperatingTime = 0;
                long engineInMotionTime = 0;
                long engineWOMotionTime = 0;
                long engineIdlingTime = 0;
                long engineNormaRpmTime = 0;
                long engineMaxRpmTime = 0;
                long engineOffTime = 0;
                long engineUnderLoadTime = 0;
                double initialFuelVolume = 0;
                double finalFuelVolume = 0;
                int day = 1;

                for (int i = 1; i <= 31; i++)
                {
                    java.sql.Date sqlDate = new Date(2021 - 1900, j, i);
                    System.out.println(sqlDate);
                    datasetOptional = datasetRepository.findByDateAndTransportId(sqlDate, e);
                    if(datasetOptional.isPresent())
                    {
                        System.out.println(datasetOptional.get());
                        day++;
                        drivingTime +=  datasetOptional.get().getDrivingTime().getTime() + 25200000;
                        engineOperatingTime +=  datasetOptional.get().getEngineOperatingTime().getTime() + 25200000;
                        engineInMotionTime    +=  datasetOptional.get().getEngineInMotionTime().getTime() + 25200000;
                        engineWOMotionTime    +=  datasetOptional.get().getEngineWOMotionTime().getTime() + 25200000;
                        engineIdlingTime    +=  datasetOptional.get().getEngineIdlingTime().getTime() + 25200000;
                        engineNormaRpmTime    +=  datasetOptional.get().getEngineNormaRpmTime().getTime() + 25200000;
                        engineMaxRpmTime    +=  datasetOptional.get().getEngineMaxRpmTime().getTime() + 25200000;
                        engineOffTime +=  datasetOptional.get().getEngineOffTime().getTime() + 25200000;
                        engineUnderLoadTime  +=  datasetOptional.get().getEngineUnderLoadTime().getTime() + 25200000;
                        initialFuelVolume  += datasetOptional.get().getInitialFuelVolume();
                        finalFuelVolume  += datasetOptional.get().getFinalFuelVolume();
                        mileage += datasetOptional.get().getMileage();
                    }
                }
                    Analise analise = new Analise();
                    analise.setMonth(j+1);
                    analise.setMileage(mileage);
                    analise.setDrivingTime(drivingTime);
                    analise.setEngineIdlingTime(engineIdlingTime);
                    analise.setEngineInMotionTime(engineInMotionTime);
                    analise.setEngineMaxRpmTime(engineMaxRpmTime);
                    analise.setEngineNormaRpmTime(engineNormaRpmTime);
                    analise.setEngineOffTime(engineOffTime);
                    analise.setTransportId(e);
                    analise.setEngineOperatingTime(engineOperatingTime);
                    analise.setEngineWOMotionTime(engineWOMotionTime);
                    analise.setFinalFuelVolume(finalFuelVolume);
                    analise.setInitialFuelVolume(initialFuelVolume);
                    analise.setEngineUnderLoadTime(engineUnderLoadTime);
                    analise.setDays(day);

                    analiseRepository.save(analise);
            }
        }


        return HttpStatus.ACCEPTED;
    }

    @RequestMapping(value = "/getAllAnaliseById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnaliseMonthResponse> getAllAnaliseById(String Id, Model model) throws ParseException
    {
        List<Analise> list = new ArrayList<>();
        analiseRepository.findAllByTransportId(Id).forEach(e -> list.add(e));

        return list.stream().map(e -> new AnaliseMonthResponse(e.getTransportId(),e.getMonth(), e.getMileage(), e.getDrivingTime(),
                e.getEngineOperatingTime(), e.getEngineInMotionTime(), e.getEngineWOMotionTime(),
                e.getEngineIdlingTime(), e.getEngineNormaRpmTime(), e.getEngineMaxRpmTime(),
                e.getEngineOffTime(), e.getEngineUnderLoadTime(), e.getInitialFuelVolume(),
                e.getFinalFuelVolume(), e.getDays() )).
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
