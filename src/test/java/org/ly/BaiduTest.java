package org.ly;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.ly.persistence.DataEntry;

import javax.persistence.*;
import java.io.IOException;

public class BaiduTest {
	
	//@Test
	public void crawlerTest() throws IOException {
		Baidu baiduCrawler = new Baidu();
        //String htmlSourceString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("bitecoin.html"));
        String htmlSourceString = baiduCrawler.getSearchedHtmlSource("上海 花店");
        baiduCrawler.getTheAd(htmlSourceString);
	}

    @Test
    public void dataBaseTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CWL");
        EntityManager em = emf.createEntityManager();
        DataEntry de = new DataEntry();
        de.setPosition("haha");
        em.getTransaction().begin();
        em.persist(de);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
