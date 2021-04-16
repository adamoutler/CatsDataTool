package com.adamoutler.cats_data_extracter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTAutoFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

/**
 *
 * @author adamo
 */
public class Tablizer {

    static long tableID = 1l;

    public Tablizer() {

    }

    public void tablize(XSSFWorkbook wb, XSSFSheet sheet, int firstRowOfTable) throws IOException {
        int numColumns = getLastPopulatedColumn(sheet);
        int numRows = getLastPopulatedRow(sheet);
        if (numberOfRowsLessThanOne(numRows)) {
            return;
        }

        this.tableAdd(wb, sheet, numRows, numColumns, firstRowOfTable);
    }

    private int getLastPopulatedColumn(XSSFSheet sheet) {
        Row row = sheet.getRow(1);

        for (int i = 1; i < row.getLastCellNum()+1; i++) {
            try {
                if (row.getCell(i) == null) {
                    return i;
                }
            } catch (NullPointerException ex) {
                return i;
            }

        }
        return 0;
    }

    private int getLastPopulatedRow(XSSFSheet sheet) {
        for (int i = 1; i < sheet.getLastRowNum() + 2; i++) {
            Row row = sheet.getRow(i);
            try {
                System.out.println(row.getCell(1));
                if (row.getCell(1) == null ) {
                    return i;
                }
            } catch (NullPointerException ex) {
                return i;
            }
        }
        return 0;
    }

    private int getLongestRow(XSSFSheet sheet) {
        int numColumns = 0;
        for (Row r : sheet) {

            if (r.getLastCellNum() > numColumns) {
                numColumns = r.getLastCellNum();
            }
        }
        return numColumns;
    }

    private boolean numberOfRowsLessThanOne(int numRows) {
        return numRows < 1;
    }

    public void tableAdd(XSSFWorkbook wb, XSSFSheet sheet, int lastRow, int lastCell, int firstRowOfTable) throws FileNotFoundException, IOException {

        AreaReference my_data_range = wb.getCreationHelper().createAreaReference(new CellReference(firstRowOfTable, 0), new CellReference(lastRow - 1,lastCell - 1 ));
        System.out.println(my_data_range.formatAsString());
        /* Create Table into Existing Worksheet */
        try {
        XSSFTable my_table = sheet.createTable(my_data_range);
        /* get CTTable object*/
        CTTable cttable = my_table.getCTTable();
        System.out.println(cttable);
        
        /* Define Styles */
        CTTableStyleInfo table_style = cttable.addNewTableStyleInfo();
        table_style.setName("TableStyleMedium9");
        /* Define Style Options */
        table_style.setShowColumnStripes(false); //showColumnStripes=0
        table_style.setShowRowStripes(true); //showRowStripes=1    
        /* Define the data range including headers */
 /* Set Range to the Table */
        cttable.setRef(my_data_range.formatAsString());
        String tableName = sheet.getSheetName().replace(" ", "");
        cttable.setDisplayName(tableName);
        /* this is the display name of the table */
        cttable.setName(tableName);
        /* This maps to "displayName" attribute in &lt;table&gt;, OOXML */
        cttable.setId(tableID++); //id attribute against table as long value
        /* Add header columns */
        int columnnumber=1;
        for (CTTableColumn c: cttable.getTableColumns().getTableColumnList()){
            c.setName("Column" + columnnumber);
            c.setId( columnnumber++);
        }
        CTAutoFilter autofilter = cttable.addNewAutoFilter();
        } catch (Exception ex){
        }

    }
}
