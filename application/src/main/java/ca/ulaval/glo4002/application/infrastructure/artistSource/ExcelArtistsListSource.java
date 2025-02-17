package ca.ulaval.glo4002.application.infrastructure.artistSource;

import ca.ulaval.glo4002.application.domain.ArtistsListSource;
import ca.ulaval.glo4002.application.domain.MoneyAmount;
import ca.ulaval.glo4002.application.domain.scheduleSimulation.Artist;
import ca.ulaval.glo4002.application.utils.ExcelFileReader;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelArtistsListSource implements ArtistsListSource {
    private static final Logger logger = LoggerFactory.getLogger(ExcelArtistsListSource.class);

    private static final String FILE_PATH = "/groups.xlsx";
    private static final int COLUMN_NAME = 0;
    private static final int COLUMN_MEMBERS = 1;
    private static final int COLUMN_STYLE = 2;
    private static final int COLUMN_PRICE = 3;
    private static final int COLUMN_POPULARITY = 4;
    private static final int COLUMN_AVAILABLE = 5;

    @Override
    public List<Artist> extractAvailableArtists() {
        try (InputStream fileInputStream = getClass().getResourceAsStream(FILE_PATH)) {
            return parseArtistsFromExcel(fileInputStream);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to read the artists Excel file: " + FILE_PATH, e);
        }
    }

    private List<Artist> parseArtistsFromExcel(InputStream fileInputStream) {
        List<Artist> artists = new ArrayList<>();
        ExcelFileReader excelReader = new ExcelFileReader(fileInputStream);

        while (excelReader.hasNextRow()) {
            Row row = excelReader.readNextRow();
            if (excelReader.isRowEmpty(row)) {
                continue;
            }

            processRow(row, artists, excelReader);
        }
        return artists;
    }

    private void processRow(Row row, List<Artist> artists, ExcelFileReader excelReader) {
        try {
            Artist artist = createArtistFromRow(row, excelReader);
            if (artist != null) {
                artists.add(artist);
            }
        }
        catch (Exception e) {
            logger.error("Skipping invalid row: " + row.getRowNum() + " due to error: " + e.getMessage());
        }
    }

    private Artist createArtistFromRow(Row row, ExcelFileReader excelReader) {
        String name = (String) excelReader.getCellValue(row, COLUMN_NAME);
        int members = ((Double) excelReader.getCellValue(row, COLUMN_MEMBERS)).intValue();
        String style = (String) excelReader.getCellValue(row, COLUMN_STYLE);
        double price = (double) excelReader.getCellValue(row, COLUMN_PRICE);
        int popularity = ((Double) excelReader.getCellValue(row, COLUMN_POPULARITY)).intValue();
        String availabilityStr = (String) excelReader.getCellValue(row, COLUMN_AVAILABLE);

        boolean availability = availabilityStr != null && availabilityStr.trim().equalsIgnoreCase("Oui");

        if (availability) {
            MoneyAmount cost = new MoneyAmount(price);
            return new Artist(name, cost, popularity, members, style);
        }
        return null;
    }

}
