/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adamoutler.cats_data_extracter;

import java.util.Comparator;
import java.util.List;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.hssf.usermodel.HSSFOptimiser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author adamo
 */
public class SheetSorter {

public static void sortSheet(XSSFWorkbook workbook, Sheet sheet) {
        //copy all rows to temp
        List<Row> rows = Lists.newArrayList(sheet.rowIterator()); 
        //sort rows in the temp
        rows.sort(Comparator.comparing(cells -> cells.getCell(0).getStringCellValue()));
        //remove all rows from sheet
        removeAllRows(sheet);
        //create new rows with values of sorted rows from temp
        for (int i = 0; i < rows.size(); i++) {
            Row newRow = sheet.createRow(i);
            Row sourceRow = rows.get(i);
            // Loop through source columns to add to new row
            for (int j = 0; j < sourceRow.getLastCellNum(); j++) {
                // Grab a copy of the old/new cell
                Cell oldCell = sourceRow.getCell(j);
                Cell newCell = newRow.createCell(j);

                // If the old cell is null jump to next cell
                if (oldCell == null) {
                    newCell = null;
                    continue;
                }

                // Copy style from old cell and apply to new cell
                CellStyle newCellStyle = workbook.createCellStyle();
                newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
                newCell.setCellStyle(newCellStyle);

                // If there is a cell comment, copy
                if (oldCell.getCellComment() != null) {
                    newCell.setCellComment(oldCell.getCellComment());
                }

                // If there is a cell hyperlink, copy
                if (oldCell.getHyperlink() != null) {
                    newCell.setHyperlink(oldCell.getHyperlink());
                }

                // Set the cell data type
                newCell.setCellType(oldCell.getCellType());

                // Set the cell data value
                switch (oldCell.getCellType()) {
                    case BLANK:
                        newCell.setCellValue(oldCell.getStringCellValue());
                        break;
                    case BOOLEAN:
                        newCell.setCellValue(oldCell.getBooleanCellValue());
                        break;
                    case ERROR:
                        newCell.setCellErrorValue(oldCell.getErrorCellValue());
                        break;
                    case FORMULA:
                        newCell.setCellFormula(oldCell.getCellFormula());
                        break;
                    case NUMERIC:
                        newCell.setCellValue(oldCell.getNumericCellValue());
                        break;
                    case STRING:
                        newCell.setCellValue(oldCell.getRichStringCellValue());
                        break;
                }
            }

            // If there are are any merged regions in the source row, copy to new row
            for (int j = 0; j < sheet.getNumMergedRegions(); j++) {
                CellRangeAddress cellRangeAddress = sheet.getMergedRegion(j);
                if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                    CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                            (newRow.getRowNum() +
                                    (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
                                    )),
                            cellRangeAddress.getFirstColumn(),
                            cellRangeAddress.getLastColumn());
                    sheet.addMergedRegion(newCellRangeAddress);
                }
            }
        }

    }

    private static void removeAllRows(Sheet sheet) {
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                sheet.removeRow(sheet.getRow(i));
            }
        }
}
