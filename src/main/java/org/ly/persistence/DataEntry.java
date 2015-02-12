package org.ly.persistence;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Created by scott on 15-2-11.
 */
@Entity
@Table(name = "DataEntry")
public class DataEntry {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private final String position;

    @Column
    private final String keyword;

    @Column
    private final String url;

    @Column
    private final String searchEngine;

    @Column
    private final Timestamp createTime;

    public DataEntry(String position, String keyword, String url, String searchEngine, Timestamp createTime) {
        this.position = position;
        this.keyword = keyword;
        this.url = url;
        this.searchEngine = searchEngine;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getUrl() {
        return url;
    }

    public String getSearchEngine() {
        return searchEngine;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
}
