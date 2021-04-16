/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adamoutler.cats_data_extracter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 *
 * @author adamo
 */
public class DataJsoner {

    public static String excelReader(Sheet datatypeSheet) throws IOException {
        List<JsonObject> list = new ArrayList<>();

        if (datatypeSheet.getSheetName().equals("Copies Required")) {
            System.out.println("foo");
            for (int i = 2; i < datatypeSheet.getLastRowNum(); i++) {
                JsonObject jsonObject = new JsonObject();
                XSSFRow row = (XSSFRow) datatypeSheet.getRow(i);
                JsonArray ja = new JsonArray();
                jsonObject.addProperty("Level", i);
                jsonObject.addProperty("copy1", row.getCell(1).getNumericCellValue());
                jsonObject.addProperty("cost1", row.getCell(2).getNumericCellValue());
                jsonObject.addProperty("token1", row.getCell(3).getNumericCellValue());
                jsonObject.addProperty("copy2", row.getCell(4).getNumericCellValue());
                jsonObject.addProperty("cost2", row.getCell(5).getNumericCellValue());
                jsonObject.addProperty("token2", row.getCell(6).getNumericCellValue());
                jsonObject.addProperty("copy3", row.getCell(7).getNumericCellValue());
                jsonObject.addProperty("cost3", row.getCell(8).getNumericCellValue());
                jsonObject.addProperty("token3", row.getCell(9).getNumericCellValue());
                jsonObject.addProperty("copy4", row.getCell(10).getNumericCellValue());
                jsonObject.addProperty("cost4", row.getCell(11).getNumericCellValue());
                jsonObject.addProperty("token4", row.getCell(12).getNumericCellValue());
                jsonObject.addProperty("copy5", row.getCell(13).getNumericCellValue());
                jsonObject.addProperty("cost5", row.getCell(14).getNumericCellValue());
                jsonObject.addProperty("token5", row.getCell(15).getNumericCellValue());
                list.add(jsonObject);
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(list);
            return json;
        }

        XSSFRow row = (XSSFRow) datatypeSheet.getRow(0);
        String TC_ID = String.valueOf(row.getCell(0));
        String TS_ID = String.valueOf(row.getCell(1));
        String Test_Steps = String.valueOf(row.getCell(2));
        String Execution_Flag = String.valueOf(row.getCell(3));
        String IdentifierType = String.valueOf(row.getCell(4));
        String IdentifierValue = String.valueOf(row.getCell(5));
        String Action_Keyword = String.valueOf(row.getCell(6));
        String Dataset = String.valueOf(row.getCell(7));
        for (int rowNumber = 1; rowNumber <= datatypeSheet.getLastRowNum(); rowNumber++) {
            JsonObject jsonObject = new JsonObject();

            XSSFRow row1 = (XSSFRow) datatypeSheet.getRow(rowNumber);

            int lastcolumn = row.getLastCellNum();
            for (int columnNumber = 0; columnNumber < row.getLastCellNum(); columnNumber++) {
                String cell = String.valueOf(row1.getCell(columnNumber));
                if (cell != null) {
                    jsonObject.addProperty("c" + columnNumber, cell);

                }

                System.out.print(jsonObject);

            }
            if (datatypeSheet.getSheetName().equals("Damage") || datatypeSheet.getSheetName().equals("Heal") || datatypeSheet.getSheetName().equals("Health") && row1.getRowNum() > 1) {
                jsonObject.addProperty("name", row1.getCell(0).getStringCellValue());

                if (row1.getCell(2) != null && row1.getCell(2).getCellType().equals(CellType.NUMERIC)) {
                    try {
                        jsonObject.addProperty("power", row1.getCell(2).getNumericCellValue());
                    } catch (Exception ex) {
                        try {
                            jsonObject.addProperty("power", Integer.parseInt(row1.getCell(2).getStringCellValue()));
                        } catch (Exception ex1) {

                        }

                    }
                }
                if (row1.getCell(1) != null && row1.getCell(1).getCellType().equals(CellType.STRING)) {
                    try {
                        jsonObject.addProperty("type", row1.getCell(1).getStringCellValue());
                    } catch (Exception ex) {
                        try {
                            jsonObject.addProperty("power", row1.getCell(1).getStringCellValue());
                        } catch (Exception ex1) {

                        }
                    }
                }

                for (int i = 6; i < row1.getLastCellNum(); i++) {
                    if (row1.getCell(i) != null && row1.getCell(i).getCellType().equals(CellType.NUMERIC)) {
                        try {
                            jsonObject.addProperty("level" + (i - 5), row1.getCell(i).getNumericCellValue());
                        } catch (Exception ex) {
                            jsonObject.addProperty("power", row1.getCell(i).getStringCellValue());
                        }
                    }
                }
                ItemHardcodedDatabase name = ItemHardcodedDatabase.reverseLookup(row1.getCell(0).getStringCellValue());
                if (name != null) {
                    jsonObject.addProperty("staticName", name.name());
                    jsonObject.addProperty("rarity", name.getRarity());
                    jsonObject.addProperty("rarityString", name.getRarityString());
                    jsonObject.addProperty("bonus", name.getBonus());
                    jsonObject.addProperty("multiplier", name.getMultiplier());
                } else {
                    jsonObject.addProperty("staticName", jsonObject.get("name").getAsString());
                    jsonObject.addProperty("name", "NEW PART!");
                    jsonObject.addProperty("rarity", 0);
                    jsonObject.addProperty("rarityString", "Unknown");
                    jsonObject.addProperty("bonus", "Unknown");
                    jsonObject.addProperty("multiplier", "Unknown");

                }

            }
            list.add(jsonObject);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(list);
        return json;
    }
}
