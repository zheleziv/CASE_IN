package case_in.db.demo.excel;

import case_in.db.demo.entity.Dataset;
import case_in.db.demo.entity.Vehicles;
import case_in.db.demo.repository.DatasetRepository;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Time;
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
        Map<List<Dataset>, Vehicles> mapVehiclesDataset = new HashMap<>();

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
        for (int i = 0; i <= max; i++)
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



        return rtrn;
    }

        public static List<Dataset> readFromExcelDataset(String file1, String file2) throws IOException, ParseException {
        String tmp;
        List<Vehicles> rtrn = new ArrayList<Vehicles>();
        Map<Integer, Vehicles> mapVehicles = new HashMap<>();
        List<Dataset> listDataset = new ArrayList<>();

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

            System.out.println("1");
        int max = sheet.getLastRowNum();
        for (int i = 0; i <= max; i++)
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
                if ((cell2.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)) {
                    if (matcher.find()) {
                        tmpVehicles = convertToVehicles(tmpString, matcher.end());
                        rtrn.add(tmpVehicles);
                        mapVehicles.put((int) cell2.getNumericCellValue(), tmpVehicles);
                    }

                }
            }
        }
        book.close();

        Workbook book1 = new HSSFWorkbook(new FileInputStream(file1));
        Sheet sheet1 = book1.getSheet("Sheet1");
        Iterator<Row> row = sheet1.iterator();
        row.next();
        while(row.hasNext())
        {
            Row tempRow = row.next();
            if((tempRow.getCell(0) != null) && (mapVehicles.keySet().contains((int)tempRow.getCell(0).getNumericCellValue())))
            {

                if(!mapVehicles.get((int)tempRow.getCell(0).getNumericCellValue()).getId().isEmpty())
                {
                    listDataset.add(convertToDataset(tempRow, mapVehicles.get((int)tempRow.getCell(0).getNumericCellValue()).getId()));
                }
            }
        }
        return listDataset;
    }

    private static double doubleVerification(Cell incell)
    {
        Pattern pattern = Pattern.compile("^\\d+");
        Matcher matcher;

        if(incell != null) {
            if (incell.getCellType() == Cell.CELL_TYPE_NUMERIC)
                return incell.getNumericCellValue();
            if (incell.getCellType() == Cell.CELL_TYPE_STRING)
            {
                matcher = pattern.matcher(incell.getStringCellValue());
                if(matcher.find())
                    return Double.parseDouble(incell.getStringCellValue());
            }


        }
        return 0.0;
    }
    private static Dataset convertToDataset(Row row, String id) throws ParseException {
        Dataset dataset = new Dataset();
        //id cell
        dataset.setTransportId(id);

        //data cell
        java.util.Date data = new SimpleDateFormat("dd.MM.yyyy").parse(row.getCell(1).getStringCellValue());
        dataset.setDate(new java.sql.Date(data.getTime()));
        //mileage cell
        dataset.setMileage(row.getCell(2).getNumericCellValue());

        //time cells
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        dataset.setDrivingTime(new java.sql.Time(formatter.parse(verificationTime(row.getCell(3))).getTime()));
        dataset.setEngineOperatingTime(new java.sql.Time(formatter.parse(verificationTime(row.getCell(4))).getTime()));
        dataset.setEngineInMotionTime(new java.sql.Time(formatter.parse(verificationTime(row.getCell(5))).getTime()));
        dataset.setEngineWOMotionTime(new java.sql.Time(formatter.parse(verificationTime(row.getCell(6))).getTime()));
        dataset.setEngineIdlingTime(new java.sql.Time(formatter.parse(verificationTime(row.getCell(7))).getTime()));
        dataset.setEngineNormaRpmTime(new java.sql.Time(formatter.parse(verificationTime(row.getCell(8))).getTime()));
        dataset.setEngineMaxRpmTime(new java.sql.Time(formatter.parse(verificationTime(row.getCell(9))).getTime()));
        dataset.setEngineOffTime(new java.sql.Time(formatter.parse(verificationTime(row.getCell(10))).getTime()));
        dataset.setEngineUnderLoadTime(new java.sql.Time(formatter.parse(verificationTime(row.getCell(11))).getTime()));
        //fuel cells
        dataset.setInitialFuelVolume(doubleVerification(row.getCell(12)));
        dataset.setFinalFuelVolume(doubleVerification(row.getCell(13)));

        return dataset;
    }


    private static String verificationTime(Cell incell)
    {
        Pattern pattern = Pattern.compile("^\\d\\d:\\d\\d:\\d\\d$");
        if((incell != null) && (incell.getCellType() == Cell.CELL_TYPE_STRING))
        {
            Matcher matcher = pattern.matcher(incell.getStringCellValue());
            if(matcher.find()) return incell.getStringCellValue();
        }

        return "00:00:00";
    }

    private static Vehicles convertToVehicles (String str, int end)
    {
        Vehicles vehicles = new Vehicles();
        Pattern pattern = Pattern.compile("(Экскаватор|Кран|Автогидроподъемник|ППУА|УМП-400|" +
                "МКД|Бетоносмеситель|ПРМ( с КМУ| )|ПКСР|Тягач( с КМУ| )|Лесовоз|Бортовой|Продукты|" +
                "Вакуум|Бульдозер|Трубоукладчик|Сварочный|Вахта|Грейдер|Виброкаток|Буровая установка|" +
                "Лаборатория|Самосвал|Вода)");
        Pattern pattern1 = Pattern.compile("[а-яА-Я]?\\d{3,4}[а-яА-Я]{2}\\d{2,3}");

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