package ca.ulaval.glo4002.application.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.Iterator;

public class ExcelFileReader {

    private final Iterator<Row> rowIterator;
    private boolean isHeaderSkipped = false;

    public ExcelFileReader(InputStream fileInputStream) {
        try {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            this.rowIterator = sheet.iterator();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read the Excel file", e);
        }
    }

    public boolean hasNextRow() {
        if (!isHeaderSkipped) {
            skipHeader();
        }
        return rowIterator.hasNext();
    }

    public Row readNextRow() {
        if (!isHeaderSkipped) {
            skipHeader();
        }
        return rowIterator.next();
    }

    public Object getCellValue(Row row, int cellIndex) {
        if (row == null) {
            throw new RuntimeException("Row is null.");
        }

        Cell cell = row.getCell(cellIndex);

        if (cell == null) {
            throw new RuntimeException("Cell at index " + cellIndex + " is missing.");
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                }
                return cell.getNumericCellValue();
            case STRING:
                return cell.getStringCellValue().trim();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case NUMERIC:
                        return cell.getNumericCellValue();
                    case STRING:
                        return cell.getStringCellValue();
                    case BOOLEAN:
                        return cell.getBooleanCellValue();
                    default:
                        throw new RuntimeException("Unsupported formula result type: " + cell.getCachedFormulaResultType());
                }
            case BLANK:
                throw new RuntimeException("Cell at index " + cellIndex + " is blank.");
            default:
                throw new RuntimeException("Unsupported cell type: " + cell.getCellType());
        }
    }

    private void skipHeader() {
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }
        isHeaderSkipped = true;
    }

    public boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }

        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String cellValue = cell.toString().trim();
                if (!cellValue.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

}

