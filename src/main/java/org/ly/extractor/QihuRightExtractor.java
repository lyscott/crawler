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
                .filter(e -> e.text().trim().equals("http://e.360.cn") ? false : true)
                .map(e -> e.text())
                .collect(Collectors.toList());
    }
}
