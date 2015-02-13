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
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BaiduTest {

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
    public void singleCrawlerTest() throws IOException {
        Baidu baiduCrawler = new Baidu();
//        String htmlSourceString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("bitecoin.html"));
        String keyword = "蝴蝶养殖";
        String htmlSourceString = null;
        try {
            htmlSourceString = baiduCrawler.getSearchedHtmlSource(keyword,0);
        } catch (Exception e) {
            //continue;
        }
        tx.begin();
        baiduCrawler.crawl(htmlSourceString, "蝴蝶养殖").forEach((de) -> em.persist(de));
        tx.commit();
        baiduCrawler.close();
    }

    @Test
    public void crawlerTest() throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream("./data/top1000.csv"));

        List<String> lines = IOUtils.readLines(is);
        for(int i = 0; i < 3000; i++) {
            String keyword = lines.get(i);
            System.out.println(keyword);
            Baidu baiduCrawler = new Baidu();
            //String htmlSourceString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("比特币_好搜.html"));
            String htmlSourceString = null;
            try {
                htmlSourceString = baiduCrawler.getSearchedHtmlSource(keyword,0);
            } catch (Exception e) {
                continue;
            }

            tx.begin();
            baiduCrawler.crawl(htmlSourceString, keyword).forEach((de) -> em.persist(de));
            tx.commit();

            baiduCrawler.close();
        }

    }
}
