package hu.bnpi.dhte.inventory.utils.readfromfile;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReadInventoryExcel {

    private Path path;

    public ReadInventoryExcel(String file) {
        this.path = Path.of(file);
    }

    public Map<String, String> getDepartmentStrings() {
        Map<String, String> departments = new HashMap<>();
        try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
            Iterator<Row> rowIterator = getRowIterator(inputStream);
            extractInventoryDistricts(departments, rowIterator);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("File not found: " + path, ioe);
        }
        return departments;
    }

    private void extractInventoryDistricts(Map<String, String> departments, Iterator<Row> rowIterator) {
        String inventoryDistrictName;
        String inventoryDistrictId;
        while (rowIterator.hasNext()) {
            Row nextRow = rowIterator.next();
            Cell firstCell = nextRow.getCell(0);
            Cell secondCell = nextRow.getCell(1);
            inventoryDistrictId = firstCell.getStringCellValue();
            inventoryDistrictName = secondCell.getStringCellValue();
            departments.put(inventoryDistrictId, inventoryDistrictName);
        }
    }

    private Iterator<Row> getRowIterator(FileInputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
        Sheet firstSheet = workbook.getSheetAt(0);
        return firstSheet.rowIterator();
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Cannot find Excel workbook in file: " + path, ioe);
        }
    }
}

