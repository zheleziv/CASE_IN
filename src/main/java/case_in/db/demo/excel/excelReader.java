package case_in.db.demo.excel;

import case_in.db.demo.entity.Dataset;
import case_in.db.demo.entity.Vehicles;
import case_in.db.demo.repository.VehiclesRepository;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import javafx.scene.shape.PathElement;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspectj.weaver.World;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jdk.nashorn.internal.objects.NativeString.substring;

public class excelReader
{

    public static List<Vehicles> readFromExcel(String file1, String file2) throws IOException, ParseException {
        String tmp;
        List<Vehicles> rtrn = new ArrayList<Vehicles>();
        Map<Integer, Vehicles> mapVehicles = new HashMap<>();

        if(!file1.contains("dataset_stage_"))
        {
            if(!file2.contains("dataset_stage_")) throw new IOException("Нет датасета");
            tmp = file1;
            file1 = file2;
            file2 = tmp;
        } else if(!file2.contains("vehicles_") && !file1.contains("vehicles_"))throw new IOException("Нет vehicles");

        Workbook book = new HSSFWorkbook(new FileInputStream(file2));
        Sheet sheet = book.getSheet("Sheet1");

        Pattern pattern = Pattern.compile("^\\d+[а-яА-Я]?\\s");
        Matcher matcher;

        int max = sheet.getLastRowNum();
        for (int i = 0; i < max; i++)
        {
            Vehicles tmpVehicles = new Vehicles();
            Row row = sheet.getRow(i);
            int maxCell = row.getLastCellNum();

            if(maxCell != 2) continue;
            Cell cell1 = row.getCell(0, Row.RETURN_BLANK_AS_NULL);
            Cell cell2 = row.getCell(1, Row.RETURN_BLANK_AS_NULL);

            if (cell1 == null || cell1.getCellType() == Cell.CELL_TYPE_BLANK) continue;
            if (cell2 == null || cell2.getCellType() == Cell.CELL_TYPE_BLANK) continue;

            if(cell1.getCellType() != HSSFCell.CELL_TYPE_STRING) continue; //throw new IOException("Нет названия транспорта");
            else {
                String tmpString = cell1.getStringCellValue();
                matcher = pattern.matcher(tmpString);
                if (matcher.find() && (cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC))
                {
                    tmpVehicles = convertToVehicles(tmpString, matcher.end());
                    rtrn.add(tmpVehicles);
                    mapVehicles.put((int)cell2.getNumericCellValue(), tmpVehicles);
                }
            }
        }
        book.close();

        for (Map.Entry<Integer, Vehicles> entry : mapVehicles.entrySet()) {
            System.out.println("1 =  " + entry.getKey() + " 2 = " + entry.getValue().getId());
        }

        Workbook book1 = new HSSFWorkbook(new FileInputStream(file1));
        Sheet sheet1 = book1.getSheet("Sheet1");
        Iterator<Row> row = sheet1.iterator();
        row.next();
        while(row.hasNext())
        {
            Row tempRow = row.next();
//            System.out.println(tempRow.getCell(0).getCellType() + " " + tempRow.getCell(0).getNumericCellValue());
//            System.out.println(mapVehicles.keySet());
            if(mapVehicles.keySet().contains((int)tempRow.getCell(0).getNumericCellValue()))
                convertToDataset(tempRow, mapVehicles.get((int)tempRow.getCell(0).getNumericCellValue()).getId());
        }

        return rtrn;
    }

    private static Dataset convertToDataset(Row row, String id) throws ParseException {
        Dataset dataset = new Dataset();
        dataset.setId(id);
        java.util.Date data = new SimpleDateFormat("dd.MM.yyyy").parse(row.getCell(1).getStringCellValue());
        dataset.setDate(new java.sql.Date(data.getTime()));
        dataset.setMileage(row.getCell(2).getNumericCellValue());
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        dataset.setDrivingTime(new java.sql.Time(formatter.parse(incell(row.getCell(3))).getTime()));
        dataset.setEngineOperatingTime(new java.sql.Time(formatter.parse(incell(row.getCell(4))).getTime()));
        dataset.setEngineInMotionTime(new java.sql.Time(formatter.parse(incell(row.getCell(5))).getTime()));
        dataset.setEngineWOMotionTime(new java.sql.Time(formatter.parse(incell(row.getCell(6))).getTime()));
        dataset.setEngineIdlingTime(new java.sql.Time(formatter.parse(incell(row.getCell(7))).getTime()));
        dataset.setEngineNormaRpmTime(new java.sql.Time(formatter.parse(incell(row.getCell(8))).getTime()));
        dataset.setEngineMaxRpmTime(new java.sql.Time(formatter.parse(incell(row.getCell(9))).getTime()));
//        dataset.setEngineOffTime(new java.sql.Time(formatter.parse(incell(row.getCell(10))).getTime()));
//        if(row.getCell(10).getCellType() == Cell.CELL_TYPE_NUMERIC) System.out.println(row.getCell(10).getNumericCellValue());
//        else if(row.getCell(10).getCellType() == Cell.CELL_TYPE_STRING) System.out.println(row.getCell(10).getStringCellValue());
        dataset.setEngineUnderLoadTime(new java.sql.Time(formatter.parse(incell(row.getCell(11))).getTime()));
        dataset.setInitialFuelVolume(0.0);
        dataset.setFinalFuelVolume(0.0);

        return dataset;
    }


    private static String incell(Cell incell)
    {
        if(incell.getCellType() == Cell.CELL_TYPE_STRING) return incell.getStringCellValue();
        return "00:00:00";
    }

    private static Vehicles convertToVehicles (String str, int end)
    {
        Vehicles vehicles = new Vehicles();
        Pattern pattern = Pattern.compile("(Экскаватор|Кран|Автогидроподъемник|ППУА|УМП-400|" +
                "МКД|Бетоносмеситель|ПРМ( с КМУ| )|ПКСР|Тягач( с КМУ| )|Лесовоз|Бортовой|Продукты|" +
                "Вакуум|Бульдозер|Трубоукладчик|Сварочный|Вахта|Грейдер|Виброкаток|Буровая установка|" +
                "Лаборатория|Самосвал|Вода)");
        Pattern pattern1 = Pattern.compile("[а-яА-Я]?[0-9]{3,4}[а-яА-Я]{2}[0-9]{2,3}");

        Matcher matcher = pattern.matcher(str);
        vehicles.setId(str.substring(0, end).trim());
        if(matcher.find()) {
            vehicles.setTransportType(str.substring(matcher.start(),matcher.end()).trim());
            Matcher matcher1 = pattern1.matcher(str);
            if(matcher1.find()) {
                vehicles.setTransportNumber(str.substring(matcher1.start(), matcher1.end()).trim());//number
                vehicles.setTransportName(str.substring(end, matcher.start()).trim());     //name
            }
            else
            {
                vehicles.setTransportNumber("Без номера");
                vehicles.setTransportName(str.substring(end, str.length()).trim());
            }
        }
        else {
            Matcher matcher1 = pattern1.matcher(str);
            vehicles.setTransportType("Разное");
            if(matcher1.find()) {
                vehicles.setTransportNumber(str.substring(matcher1.start(), matcher1.end()).trim());//number
                vehicles.setTransportName(str.substring(end, matcher1.start()).trim());     //name
            }
            else
            {
                vehicles.setTransportNumber("Без номера");
                vehicles.setTransportName(str.substring(end, str.length()).trim());
            }
        }
        return vehicles;
    }

}
