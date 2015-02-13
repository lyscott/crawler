package org.ly;

import org.apache.commons.io.IOUtils;
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
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class QihuTest {

    EntityManager em;
    EntityManagerFactory emf;
    EntityTransaction tx;
    protected static Logger LOGGER = LoggerFactory.getLogger(QihuTest.class);

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
        //String htmlSourceString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("比特币_好搜.html"));
        String keyword = "上海注册公司";
        String htmlSourceString = null;
        try {
            htmlSourceString = qihuCrawler.getSearchedHtmlSource(keyword, 0);
        } catch (Exception e) {
            //continue;
        }
        tx.begin();
        qihuCrawler.crawl(htmlSourceString, "上海注册公司").forEach((de) -> em.persist(de));
        tx.commit();
        List<DataEntry> des = em.createQuery("select de from DataEntry de", DataEntry.class).getResultList();
//        Assert.assertTrue(des.size() == 4);
        qihuCrawler.close();

    }

    @Test
    public void readFileTest() throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream("./data/top1000.csv"));
        List<String> lines = IOUtils.readLines(is);
        Qihu qihuCrawler = new Qihu();
//        Set<String> s = new LinkedHashSet<String>(lines);
//        lines = Arrays.asList(s.toArray(new String[]{}));
        for (int i = 639; i < 3000; i++) {
            String keyword = lines.get(i);
            System.out.println(keyword);

            //String htmlSourceString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("比特币_好搜.html"));
            String htmlSourceString = null;
            try {
                htmlSourceString = qihuCrawler.getSearchedHtmlSource(keyword, 0);
            } catch (Exception e) {
                LOGGER.error("Error in test:", e);
                continue;
            }

            tx.begin();
            qihuCrawler.crawl(htmlSourceString, keyword).forEach((de) -> em.persist(de));
            tx.commit();
//            List<DataEntry> des = em.createQuery("select de from DataEntry de", DataEntry.class).getResultList();
//        Assert.assertTrue(des.size() == 4);

        }
        qihuCrawler.close();
    }

    @Test
    public void xxx() throws IOException, InterruptedException {
        InputStream is = new BufferedInputStream(new FileInputStream("./data/top1000.csv"));
        List<String> lines = IOUtils.readLines(is);
        Set<String> s = new LinkedHashSet<>(lines);
        lines = Arrays.asList(s.toArray(new String[]{}));
        final List<String> finalLines = lines.subList(0, 10000);

        List<Crawler> crawlers = Arrays.asList(new Qihu(), new Baidu());

        List<Thread> threads = crawlers.stream().map(c -> new Thread(new CrawlerActor(c, emf.createEntityManager(), finalLines))).collect(Collectors.toList());
        threads.forEach((t) -> t.start());
        threads.parallelStream().forEach((t) -> {
            try {
                t.join();
            } catch (InterruptedException ie) {
                LOGGER.error("cant join thread", ie);
            }
        });
    }

    class CrawlerActor implements Runnable {
        private final Crawler crawler;
        private final EntityManager em;
        private final Queue<String> queue;

        public CrawlerActor(Crawler c, EntityManager e, List<String> words) {
            crawler = c;
            em = e;
            queue = new ArrayDeque<>(words);
        }

        @Override
        public void run() {
            while (!queue.isEmpty()) {
                String keyword = queue.poll();
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                try {
                    List<DataEntry> de = crawler.crawl(crawler.getSearchedHtmlSource(keyword, 0), keyword);
                    de.forEach(e -> em.persist(e));
                } catch (Exception e) {
                    LOGGER.error("error in CrawlerActor", e);
                } finally {
                    transaction.commit();
                }
            }
            crawler.close();
        }
    }
}
