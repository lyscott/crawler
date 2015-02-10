package org.ly;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

public class QiHuTest {
	
	@Test
	public void crawlerTest() throws IOException {
		QiHu qihuCrawler = new QiHu();
        //String htmlSourceString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("bitecoin.html"));
        String htmlSourceString = qihuCrawler.getSearchedHtmlSource("网上购物");
        qihuCrawler.getTheAd(htmlSourceString,"网上购物");
	}
}
