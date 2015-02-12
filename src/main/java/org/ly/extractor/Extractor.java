package org.ly.extractor;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by scott on 15-2-13.
 */
public interface Extractor {
    public List<String> extract(Document document, String keyword);
}
