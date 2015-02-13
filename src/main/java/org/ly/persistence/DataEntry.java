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
    private String position;

    @Column
    private String keyword;

    @Column
    private String url;

    @Column
    private String searchEngine;

    @Column
    private Timestamp createTime;

    public DataEntry(){}

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

    @Override
    public String toString() {
        return keyword+","+position;
    }
}
