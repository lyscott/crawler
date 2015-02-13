package org.ly.report;

import com.google.common.base.Joiner;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ly.persistence.DataEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by scott on 15-2-14.
 */
public class ReportGeneratorTest {

    protected static Logger LOGGER = LoggerFactory.getLogger(ReportGeneratorTest.class);
    EntityManager em;
    EntityManagerFactory emf;
    EntityTransaction tx;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("CWL");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void createKeywordTest() throws IOException {

        List<DataEntry> des = em.createQuery("select de from DataEntry de", DataEntry.class).getResultList();
        ReportGenerator rg = new ReportGenerator();
        LOGGER.info("{}", des);
        List<ReportEntry> report = rg.dataToReport(des);
        LOGGER.info("{}", report);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sample sheet");

        HSSFCellStyle cellStyle=workbook.createCellStyle();
        cellStyle.setWrapText(true);

        int rownum = 1;
        for(ReportEntry re : report){
            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            Cell cell = row.createCell(cellnum++);
            cell.setCellValue(re.getKeyword());
            Map<AddressInfo, List<String>> urls = re.getUrls();

            for(Map.Entry<AddressInfo, List<String>> entry: urls.entrySet()){
                cell = row.createCell(cellnum++);
                //cell.setCellStyle(cellStyle);
                cell.setCellValue(Joiner.on("\r\n").join(entry.getValue()));
                //AddressInfo addressInfo = entry.getKey();

            }

        }

        try {
            FileOutputStream out =
                    new FileOutputStream(new File("/home/scott/workspace/new.xls"));
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
