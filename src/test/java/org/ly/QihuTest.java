package org.ly;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ly.persistence.DataEntry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.*;
import java.util.List;

public class QihuTest {

    EntityManager em;
    EntityManagerFactory emf;
    EntityTransaction tx;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("CWL");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void crawlerTest() throws IOException {
        Qihu qihuCrawler = new Qihu();
        String htmlSourceString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("比特币_好搜.html"));
        //String htmlSourceString = qihuCrawler.getSearchedHtmlSource("上海 花店");
        tx.begin();
        qihuCrawler.crawl(htmlSourceString, "比特币").forEach((de) -> em.persist(de));
        tx.commit();
        List<DataEntry> des = em.createQuery("select de from DataEntry de", DataEntry.class).getResultList();
//        Assert.assertTrue(des.size() == 4);
        qihuCrawler.close();

    }

    @Test
    public void readFileTest() throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream("./data/medical.csv"));

        List<String> lines = IOUtils.readLines(is);
        for(int i = 0; i < 30; i++) {
            String keyword = lines.get(i);
            System.out.println(keyword);
            Qihu qihuCrawler = new Qihu();
            //String htmlSourceString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("比特币_好搜.html"));
            String htmlSourceString = qihuCrawler.getSearchedHtmlSource(keyword);
            tx.begin();
            qihuCrawler.crawl(htmlSourceString, keyword).forEach((de) -> em.persist(de));
            tx.commit();
//            List<DataEntry> des = em.createQuery("select de from DataEntry de", DataEntry.class).getResultList();
//        Assert.assertTrue(des.size() == 4);
            qihuCrawler.close();
        }

    }
}
