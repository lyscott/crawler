package org.ly;

import org.junit.Test;

public class BaiduTest {
	
	@Test
	public void crawlerTest() {
		Baidu baiduCrawler = new Baidu();
		String htmlSourceString = baiduCrawler.getSearchedHtmlSource("北京花店");
		baiduCrawler.getTheTopAd(htmlSourceString);
	}
}
