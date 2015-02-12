package org.ly.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.ly.persistence.DataEntry;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by scott on 15-2-13.
 */
public class QihuRightExtractor implements Extractor {
    @Override
    public List<String> extract(Document document, String keyword) {
        Elements elements = document.select("div[id=right_show] ul cite");

        return elements.stream()
                .map((e) -> {
                    String url = "";
                    if (e.text().indexOf("http://e.360.cn")==-1) {
                        url = e.text();
                    } else {
                        url = "";
                    }
                    return url;
                })
                .collect(Collectors.toList());
    }
}
