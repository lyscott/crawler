package org.ly.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.ly.persistence.DataEntry;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by scott on 16-2-13.
 */
public class QihuContentExtractor implements Extractor {

    @Override
    public List<String> extract(Document document, String keyword) {
        Elements elements = document.select("div[id=sunrise] cite a");
        return elements.stream()
                .map((e) -> {
                    String url = e.attr("href");
                    return url;
                })
                .collect(Collectors.toList());
    }
}
