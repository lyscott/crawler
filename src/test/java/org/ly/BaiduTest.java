package org.ly;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

public class BaiduTest {
	
	@Test
	public void crawlerTest() throws IOException {
		Baidu baiduCrawler = new Baidu();
        //String htmlSourceString = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("bitecoin.html"));
        String htmlSourceString = baiduCrawler.getSearchedHtmlSource("上海 花店");
        baiduCrawler.getTheAd(htmlSourceString);
	}
}
