package ca.ulaval.glo4002.application.utils;

import org.apache.poi.ss.usermodel.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class ExcelFileReaderTest {

    private static final String TEST_FILE = "/test_groups.xlsx";
    private static final String EMPTY_TEST_FILE = "/emptyExcel_Test.xlsx";
    private static final int EXPECTED_AVAILABLE_ARTISTS = 16;

    private ExcelFileReader fileReader;

    @BeforeEach
    void setUp() {
        InputStream testFile = getClass().getResourceAsStream(TEST_FILE);
        assertNotNull(testFile);
        fileReader = new ExcelFileReader(testFile);
    }

    @Test
    void givenValidFile_whenReadingAllRows_thenReturnsOnlyAvailableArtists() {
        int availableArtistsCount = countAvailableArtists();

        assertEquals(EXPECTED_AVAILABLE_ARTISTS, availableArtistsCount);
    }

    @Test
    void givenValidFile_whenHasNextRow_thenReturnsTrue() {
        boolean hasNextRow = fileReader.hasNextRow();

        assertTrue(hasNextRow);
    }

    @Test
    void givenEmptyFile_whenHasNextRow_thenReturnsFalse() {
        InputStream emptyFile = getClass().getResourceAsStream(EMPTY_TEST_FILE);
        assertNotNull(emptyFile);
        ExcelFileReader emptyFileReader = new ExcelFileReader(emptyFile);

        boolean hasNextRow = emptyFileReader.hasNextRow();

        assertFalse(hasNextRow);
    }

    @Test
    void givenValidRow_whenGetCellValue_thenReturnsCorrectStringValue() {
        Row row = fileReader.readNextRow();
        final String EXPECTED_NAME = "Sun 41";

        String name = (String) fileReader.getCellValue(row, ExcelFileReaderTestHelper.COLUMN_NAME);

        assertEquals(EXPECTED_NAME, name);
    }

    @Test
    void givenValidRow_whenGetCellValue_thenReturnsCorrectIntegerValue() {
        Row row = fileReader.readNextRow();
        final int EXPECTED_GROUP_SIZE = 5;

        int groupSize = ((Double) fileReader.getCellValue(row, ExcelFileReaderTestHelper.COLUMN_GROUP_SIZE)).intValue();

        assertEquals(EXPECTED_GROUP_SIZE, groupSize);
    }

    @Test
    void givenValidRow_whenGetCellValue_thenReturnsCorrectDoubleValue() {
        Row row = fileReader.readNextRow();
        final double EXPECTED_COST = 69000.0;
        final double DELTA = 0.01;

        double cost = (double) fileReader.getCellValue(row, ExcelFileReaderTestHelper.COLUMN_COST);

        assertEquals(EXPECTED_COST, cost, DELTA);
    }

    @Test
    void givenMissingCell_whenGetCellValue_thenThrowsRuntimeException() {
        Row row = fileReader.readNextRow();

        assertThrows(RuntimeException.class, () -> fileReader.getCellValue(row, 99));
    }

    @Test
    void givenInvalidInputStream_whenCreatingFileReader_thenThrowsRuntimeException() {
        assertThrows(RuntimeException.class, () -> new ExcelFileReader(null));
    }

    private int countAvailableArtists() {
        int availableArtistsCount = 0;

        while (fileReader.hasNextRow()) {
            Row row = fileReader.readNextRow();
            String availability = (String) fileReader.getCellValue(row, ExcelFileReaderTestHelper.COLUMN_AVAILABILITY);
            if ("Oui".equalsIgnoreCase(availability)) {
                availableArtistsCount++;
            }
        }

        return availableArtistsCount;
    }
}

class ExcelFileReaderTestHelper {
    static final int COLUMN_NAME = 0;
    static final int COLUMN_GROUP_SIZE = 1;
    static final int COLUMN_COST = 3;
    static final int COLUMN_AVAILABILITY = 5;
}
