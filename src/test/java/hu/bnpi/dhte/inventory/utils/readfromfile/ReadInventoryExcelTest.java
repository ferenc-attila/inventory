package hu.bnpi.dhte.inventory.utils.readfromfile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReadInventoryExcelTest {

    private ReadInventoryExcel readInventoryExcel;
    private String separator;

    @BeforeEach
    void init() {
        readInventoryExcel = new ReadInventoryExcel("src/test/resources/inventoryExcel.xlsx");
        separator = System.getProperty("file.separator");
    }

    @Test
    void getDepartmentStringsTest() {
        Map<String, String> result = readInventoryExcel.getDepartmentStrings();
        assertThat(result).hasSize(178)
                .contains(new AbstractMap.SimpleEntry<>("006", "szolgálati lakás"),
                        new AbstractMap.SimpleEntry<>("999", "nincs konkrét helység"));
    }

    @Test
    void getDepartmentStringsInvalidPathTest() {
        readInventoryExcel = new ReadInventoryExcel("src/test/resources/no_such_file.xlsx");
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> readInventoryExcel.getDepartmentStrings());
        assertThat(iae).hasMessage("File not found: src"+ separator + "test" + separator + "resources" + separator + "no_such_file.xlsx")
                .getCause().isInstanceOf(IOException.class);
    }
}