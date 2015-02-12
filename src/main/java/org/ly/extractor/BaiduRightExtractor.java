package org.ly.extractor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by scott on 15-2-13.
 */
public class BaiduRightExtractor implements Extractor {
    @Override
    public List<String> extract(Document document, String keyword) {
        Elements elements = document.select("#ec_im_container font[size=-1][class~=^()");

        return elements.stream()
                .map((e) -> {
                    String url = e.text();
                    return url;
                })
                .collect(Collectors.toList());
    }
}
