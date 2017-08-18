/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月26日 下午8:09:01 * 
*/ 
package com.dazk.db.model;

import javax.persistence.*;

/**
 * 基础信息
 *
 * @author liuzh
 * @since 2016-01-31 21:42
 */
public class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Integer page;
    
    @Transient
    private String name;

    @Transient
    private Integer listRows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getListRows() {
        return listRows;
    }

    public void setListRows(Integer listRows) {
        this.listRows = listRows;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}    
    
}

