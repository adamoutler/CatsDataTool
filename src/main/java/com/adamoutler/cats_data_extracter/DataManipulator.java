/*
*extracts and prints the json portion of the message cache from CATS
*    Copyright (C) 2018  Adam Outler <babysharks@adamoutler.com>
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.adamoutler.cats_data_extracter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.examples.html.ToHtml;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataManipulator {

    //run with java -jar DataManipulator.jar --output=cats.xlsx --adb="C:\Program Files\Nox\bin\nox_adb.exe"
    static String machineName = "127.0.0.1:62001";
    static String fileLocation = "msgCache.bin";
    static String outputFile = "/var/www/baby-sharks/parts/CatsParts-" + System.currentTimeMillis() + ".xlsx";
    public static AppParams params;
    static String adbLocation = "adb";
    ProcessBuilder connect = new ProcessBuilder(new String[]{adbLocation, "connect", machineName});
    static ProcessBuilder refresh = new ProcessBuilder(new String[]{adbLocation, "-s", machineName, "shell", "rm /data/data/com.zeptolab.cats.google/cache/msgCache.bin;am restart; sleep 30; am start  com.zeptolab.cats.google/com.zeptolab.cats.CATSActivity; sleep 40;"});
    ProcessBuilder pb = new ProcessBuilder(new String[]{adbLocation, "-s", machineName, "pull", "/data/data/com.zeptolab.cats.google/cache/msgCache.bin"});
    static boolean debugmode = false;
    static Properties p = new Properties();

    public static void main(String[] args) {
        params = new AppParams(args);
        Map<String, String> l = params.getNamed();
        outputFile = params.getNamed().getOrDefault("output", outputFile);
        adbLocation = params.getNamed().getOrDefault("adb", adbLocation);
        machineName = params.getNamed().getOrDefault("machine", machineName);
        File f = new File("msgCache.bin");
        if (params.getUnnamed().contains("purge")) {
            deleteExistingFile(f);
        }
        try {
            if (!debugmode) {
                prepareEnvironment();
                extractMsgCache();
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DataManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print("Working with local file: " + f.getAbsolutePath());

        try {
            processMsgCache();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataManipulator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void processMsgCache() throws IOException {
        try (BufferedReader fr = new BufferedReader(new FileReader(fileLocation))) {
            fr.lines().forEach((String line) -> {
                if (line.contains("ultimateBoosterDiscountRate")) {

                    line = line.substring(0, line.lastIndexOf("}") + 1).substring(line.indexOf("{"));

                    try {
                        XSSFSheet sheet = createExcelWorkbook(line);

                        System.out.println(sheet);

                    } catch (IOException ex) {
                        Logger.getLogger(DataManipulator.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    line = line.replace("{", "\n{").replace("}", "}\n")
                            .replace("],", "],\n");
                    System.out.println(line);
                }
            });
        }
    }

    private static XSSFSheet createExcelWorkbook(String line) throws IOException, JsonSyntaxException {
        JsonParser parser = new JsonParser();
        InputStream is = DataManipulator.class.getResourceAsStream("/translations.properties");
        p.load(is);
        JsonObject rootObject = parser.parse(line).getAsJsonObject();

        JsonObject damage = rootObject.getAsJsonObject("ultimateDamageMap");

        JsonObject health = rootObject.getAsJsonObject("ultimateHealthMap");
        JsonObject heal = rootObject.getAsJsonObject("ultimateHealMap");
        JsonArray rarityCost = rootObject.getAsJsonArray("ultimatePartUpgradeCostMapByRarityAndLevel");
        JsonArray copiesRequired = rootObject.getAsJsonArray("ultimateVehiclePartUpgradeLevelRequiredCopiesByRarityAndLevel");
        JsonArray tokens = rootObject.getAsJsonArray("ultimatePartUpgradeTokenCostMapByRarityAndLevel");
        JsonObject power = rootObject.getAsJsonObject("ultimatePowerMap");
        File f = new File(outputFile);
        f.getParentFile().mkdirs();
        if (!f.exists()) {
            f.createNewFile();
        }
        XSSFWorkbook wb = new XSSFWorkbook();
        writeDualArray(damage, wb.createSheet("Damage"), power);
        writeDualArray(health, wb.createSheet("Health"), power);
        writeDualArray(heal, wb.createSheet("Heal"), power);
        XSSFSheet sheet = createSheetFromMultipleArrayMinusOne(wb, "Copies Required", tokens, copiesRequired, rarityCost);

        wb.setForceFormulaRecalculation(true);
        Tablizer tablizer = new Tablizer();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            XSSFSheet activeSheet = wb.getSheetAt(i);
            activeSheet.getRow(0).createCell(0).setCellValue("");
            if (activeSheet.getSheetName().equals("Copies Required")) {
                //SheetSorter.sortSheet(activeSheet, 1, 2);
                //   SheetSorter.sortSheet(activeSheet, 2, 2);
                tablizer.tablize(wb, activeSheet, 1);
            } else {
                tablizer.tablize(wb, activeSheet, 0);
            }
            String jsonFile = outputFile + "-" + activeSheet.getSheetName() + ".json";
            try (FileOutputStream fos = new FileOutputStream(jsonFile)) {
                fos.write(DataJsoner.excelReader(activeSheet).getBytes());
                System.out.println("Wrote " + jsonFile);
            }

        }
        wb.getSheet("Heal").getRow(0).createCell(0).setCellValue("power-part\\level");
        wb.getSheet("Health").getRow(0).createCell(0).setCellValue("health-part\\level");
        wb.getSheet("Damage").getRow(0).createCell(0).setCellValue("attack-part\\level");
        sheet.getRow(0).createCell(0).setCellValue("copies-cost");
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            XSSFSheet activeSheet = wb.getSheetAt(i);

            for (int c = 0; c < activeSheet.getRow(1).getPhysicalNumberOfCells(); c++) {
                activeSheet.autoSizeColumn(c);
            }
        }

        String s = DataJsoner.excelReader(sheet);
        try (FileOutputStream fos = new FileOutputStream(f)) {
            wb.write(fos);
            System.out.println("wrote " + f.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Failed to write json");
        }
        if (!GraphicsEnvironment.isHeadless()) {
            Desktop d = Desktop.getDesktop();
            System.out.println("result: " + f.getAbsolutePath());
            d.open(f);
        }
//            ToHtml toHtml = create(args[0], pw);
//            toHtml.setCompleteHTML(true);
//            toHtml.printPage();

        FileWriter fw = new FileWriter(f.getAbsolutePath() + ".html");
        PrintWriter pw = new PrintWriter(fw);
        ToHtml toHtml = ToHtml.create(f.getAbsolutePath(), pw);
        toHtml.setCompleteHTML(true);
        toHtml.printPage();
        return sheet;
    }

    private static void writeDualArray(JsonObject data, XSSFSheet sheet3, JsonObject power) {

        AtomicInteger rowNumber = new AtomicInteger(1);
        XSSFRow r0 = sheet3.createRow(0);
        r0.createCell(1).setCellValue("Type   ");
        r0.createCell(2).setCellValue("Power  ");
        r0.createCell(3).setCellValue("Rarity");
        r0.createCell(4).setCellValue("multiplier");
        r0.createCell(5).setCellValue("bonus  ");

        Set<Entry<String, JsonElement>> sets = data.entrySet();
        sets.stream().sorted((a, b) -> {
            return a.getKey().compareTo(b.getKey());
        }).forEach(entry -> {
            while (sheet3.getLastRowNum() < rowNumber.get()) {
                sheet3.createRow(sheet3.getLastRowNum() + 1);
            }

            XSSFRow row = sheet3.getRow(rowNumber.getAndIncrement());
            XSSFCell name = row.createCell(0);
            XSSFCell type = row.createCell(1);
            type.setCellType(CellType.STRING);
            XSSFCell rarity = row.createCell(3);
            XSSFCell multiplier = row.createCell(4);
            XSSFCell bonus = row.createCell(5);
            String cellVal = p.getProperty((String) entry.getKey(), (String) entry.getKey()).replace("'", "").replace(" ", "").replace("-", "");

            ItemHardcodedDatabase ren = ItemHardcodedDatabase.UNKNOWN; 
            try {
                ren = ItemHardcodedDatabase.valueOf(cellVal);
                name.setCellValue(ren.getString());
                type.setCellValue(ren.getItemType().name());
                rarity.setCellValue(ren.getRarity() + " " + ren.getRarityString());
                multiplier.setCellValue(ren.getMultiplier());
                bonus.setCellValue(ren.getBonus());

            } catch (Exception ex) {
                name.setCellValue(cellVal);
            }
            try {
                Integer o = power.get((String) entry.getKey()).getAsJsonArray().get(1).getAsInt();
                setCellValue(row, 2, o);
            } catch (Exception ex) {
                row.createCell(2).setBlank();

            }
            JsonArray array = (JsonArray) entry.getValue();
            for (int i = 0; i < array.size(); i++) {
                XSSFCell c = row.createCell(i + 6);
                c.setCellType(CellType.NUMERIC);
                c.setCellValue((int) Double.parseDouble(array.get(i).toString()));
            }
            row.createCell(row.getLastCellNum(), CellType.STRING).setCellValue(ren.name());

        });
        for (int i = 6; i < sheet3.getRow(1).getLastCellNum() - 1; i++) {
            XSSFCell c = r0.createCell(i);
            c.setCellType(CellType.STRING);
            c.setCellValue("Level " + (i - 5));
        }
        r0.createCell(sheet3.getRow(1).getLastCellNum() - 1, CellType.STRING).setCellValue("Internal Name");
    }

    private static XSSFSheet createSheetFromMultipleArray(XSSFWorkbook wb, String name, JsonArray copiesRequired) {
        XSSFSheet sheet;
        sheet = wb.createSheet(name);
        int column = 1;
        XSSFRow r0 = sheet.createRow(0);

        for (int i = 0; i < copiesRequired.size(); i++) {
            JsonArray arr = (JsonArray) copiesRequired.get(i);
            for (int j = 0; j < arr.size(); j++) {

                while (sheet.getLastRowNum() < arr.size()) {
                    sheet.createRow(sheet.getLastRowNum() + 1);
                }
                XSSFRow currentRow = sheet.getRow(j + 1);
                currentRow.createCell(0).setCellValue("Level " + (j + 1));
                XSSFCell c = currentRow.createCell(column);
                c.<String>setCellValue((int) Double.parseDouble(arr.get(j).getAsString()));
                c.setCellType(CellType.NUMERIC);
            }
            column++;
            System.out.println(arr);

        }
        XSSFRow row = sheet.createRow(0);
        XSSFRow r = sheet.createRow(0);
        r.createCell(1).<String>setCellValue((String) "");
        r.createCell(2).<String>setCellValue("");
        r.createCell(3).<String>setCellValue("");
        r.createCell(4).<String>setCellValue("");
        r.createCell(5).<String>setCellValue("");
        return sheet;
    }

    private static XSSFSheet createSheetFromMultipleArrayMinusOne(XSSFWorkbook wb, String name, JsonArray tokens, JsonArray copiesRequired, JsonArray cost) {
        XSSFSheet sheet;

        sheet = wb.createSheet(name);

        int column = 1;
        for (int i = 0; i < copiesRequired.size(); i++) {
            JsonArray arr = (JsonArray) copiesRequired.get(i);
            JsonArray token = (JsonArray) tokens.get(i);
            for (int j = 0; j < arr.size() - 1; j++) {
                while (sheet.getLastRowNum() < arr.size() + 1) {
                    XSSFRow r = sheet.createRow(sheet.getLastRowNum() + 1);

                }
                XSSFRow currentRow = sheet.getRow(j + 2);
                currentRow.createCell(0).setCellValue("Level " + (j + 2));
                XSSFCell c = currentRow.createCell(column);
                Integer value = ((int) arr.get(j).getAsInt());
                value--;
                c.<String>setCellValue(value);
                c.setCellType(CellType.NUMERIC);
                XSSFCell d = currentRow.createCell(column + 1);
                Integer value2 = ((int) ((JsonArray) cost.get(i)).get(j).getAsInt());
                d.<String>setCellValue(value2);
                d.setCellType(CellType.NUMERIC);
                Integer tokenvalue = ((int) token.get(j).getAsInt());
                XSSFCell tokenCell = currentRow.createCell(column + 2);
                tokenCell.<String>setCellValue(tokenvalue);
                tokenCell.setCellType(CellType.NUMERIC);

            }
            column = column + 3;
            System.out.println(arr);

        }
        XSSFRow r = sheet.createRow(0);
        setCellValue(r, 1, "Sta  ");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 3));
        setCellValue(r, 4, "Pol");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 6));

        setCellValue(r, 7, "Ref");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 9));

        setCellValue(r, 10, "Sup");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 12));

        setCellValue(r, 13, "Out");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 15));

        XSSFRow s = sheet.createRow(1);
        setCellValue(s, 0, "level\\rarity");
        setCellValue(s, 1, "copy 1");
        setCellValue(s, 2, "cost 1");
        setCellValue(s, 3, "tok 1");
        setCellValue(s, 4, "copy 2");
        setCellValue(s, 5, "cost 2");
        setCellValue(s, 6, "tok 2");
        setCellValue(s, 7, "copy 3");
        setCellValue(s, 8, "cost 3");
        setCellValue(s, 9, "tok 3");
        setCellValue(s, 10, "copy 4");
        setCellValue(s, 11, "cost 4");
        setCellValue(s, 12, "tok 4");
        setCellValue(s, 13, "copy 5");
        setCellValue(s, 14, "cost 5");
        setCellValue(s, 15, "tok 5");
        
        

        return sheet;
    }

    private static void extractMsgCache() throws IOException, InterruptedException {
        System.out.println("Extracting cache");
        ProcessBuilder pb = new DataManipulator().pb;
        pb.redirectError();
        Process p = pb.start();
        p.waitFor(5, TimeUnit.SECONDS);
        InputStream is = p.getInputStream();
        while (is.available() > 0) {
            System.out.print((char) is.read());

        }
        System.out.println();
    }

    private static void setCellValue(XSSFRow row, int cellNumber, String value) {
        row.createCell(cellNumber).<String>setCellValue(value);
    }

    private static void setCellValue(XSSFRow row, int cellNumber, int value) {
        row.createCell(cellNumber).<Integer>setCellValue(value);
    }

    private static void prepareEnvironment() throws InterruptedException, IOException {
        System.out.println("Connecting to system");
        ProcessBuilder connect = new DataManipulator().connect;

        connect.start().waitFor(20, TimeUnit.SECONDS);
        if (params.getRaw().contains("--purge")) {
            ProcessBuilder refresh = new DataManipulator().refresh;
            System.out.println("purging old data");
            refresh.start().waitFor(45, TimeUnit.SECONDS);
        }
    }

    private static void deleteExistingFile(File f) {
        if (f.exists()) {
            f.delete();
        }
    }

}
