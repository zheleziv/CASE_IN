package case_in.db.demo.controllers;

import case_in.db.demo.entity.Dataset;
import case_in.db.demo.entity.Vehicles;
import case_in.db.demo.excel.excelReader;
import case_in.db.demo.repository.DatasetRepository;
import case_in.db.demo.repository.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
public class FileUploadController {
    @Autowired
    VehiclesRepository vehiclesRepository;
    @Autowired
    DatasetRepository datasetRepository;

    @RequestMapping(value="/upload", method= RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "Вы можете загружать файл с использованием того же URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("files") MultipartFile[] files)
    {
        MultipartFile file1 = files[0];
        MultipartFile file2 = files[1];
        String name1 = file1.getOriginalFilename();
        String name2 = file2.getOriginalFilename();
        System.out.println(name1 + " " + name2);
        if (!file1.isEmpty() && !file2.isEmpty()) {
            try {
                byte[] bytes1 = file1.getBytes();
                BufferedOutputStream stream1 = new BufferedOutputStream(new FileOutputStream(new File(name1)));
                stream1.write(bytes1);
                stream1.close();

                byte[] bytes2 = file2.getBytes();
                BufferedOutputStream stream2 = new BufferedOutputStream(new FileOutputStream(new File(name2)));
                stream2.write(bytes2);
                stream2.close();

                List<Vehicles> listVehicles = excelReader.readFromExcel(name1, name2);
                List<Dataset> listDataset = excelReader.readFromExcelDataset(name1, name2);
                for(Vehicles vehicles : listVehicles)
                {
                    if(!vehiclesRepository.findById(vehicles.getId()).isPresent()) vehiclesRepository.save(vehicles);
                }

                for(Dataset dataset : listDataset)
                {
                    if(!datasetRepository.findByDateAndTransportId(dataset.getDate(), dataset.getTransportId())
                            .isPresent())
                        datasetRepository.save(dataset);
                }

                return "Вы удачно загрузили " + name1 + " и " + name2 + " на сервер!";
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name1 + " или " + name2 + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить файлы, потому что есть пустой файл.";
        }
    }

}
