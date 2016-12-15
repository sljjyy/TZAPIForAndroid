package com.tianzunchina.sample.event;


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

/**
 * admin
 * 2016/9/8 0008.
 */
@Table(name = "Event")
public class Event  {
    @Column(name = "ID", isId = true)
    private int ID;
    @Column(name = "NO")
    private String NO;
    @Column(name = "address")
    private String address;
    @Column(name = "description")
    private String description;
    private List<String> path;
    @Column(name = "path")
    private String pathStr;
}
