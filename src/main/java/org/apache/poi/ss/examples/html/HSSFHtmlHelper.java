/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.poi.ss.examples.html;

import java.util.Formatter;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Implementation of {@link HtmlHelper} for HSSF files.
 */
public class HSSFHtmlHelper implements HtmlHelper {
    private final HSSFWorkbook wb;
    private final HSSFPalette colors;

    private static final HSSFColor HSSF_AUTO = HSSFColorPredefined.AUTOMATIC.getColor();

    public HSSFHtmlHelper(HSSFWorkbook wb) {
        this.wb = wb;
        // If there is no custom palette, then this creates a new one that is
        // a copy of the default
        colors = wb.getCustomPalette();
    }

    @Override
    public void colorStyles(CellStyle style, Formatter out) {
        HSSFCellStyle cs = (HSSFCellStyle) style;
        out.format("  /* fill pattern = %d */%n", cs.getFillPattern().getCode());
        styleColor(out, "background-color", cs.getFillForegroundColor());
        styleColor(out, "color", cs.getFont(wb).getColor());
        styleColor(out, "border-left-color", cs.getLeftBorderColor());
        styleColor(out, "border-right-color", cs.getRightBorderColor());
        styleColor(out, "border-top-color", cs.getTopBorderColor());
        styleColor(out, "border-bottom-color", cs.getBottomBorderColor());
    }

    private void styleColor(Formatter out, String attr, short index) {
        HSSFColor color = colors.getColor(index);
        if (index == HSSF_AUTO.getIndex() || color == null) {
            out.format("  /* %s: index = %d */%n", attr, index);
        } else {
            short[] rgb = color.getTriplet();
            out.format("  %s: #%02x%02x%02x; /* index = %d */%n", attr, rgb[0],
                    rgb[1], rgb[2], index);
        }
    }
}