/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月26日 上午8:37:34 * 
*/
package com.dazk.db.model;



import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//注意Menu对应的表名为resource
@Table(name = "resource")
public class Menu extends BaseEntity {
	// `id` int(11) NOT NULL AUTO_INCREMENT,	
	private Long id;
	// `name` varchar(20) NOT NULL COMMENT '名称',
	private String name;
	// `code` varchar(20) NOT NULL COMMENT '编号',
	private String code;
	// `url` varchar(45) NOT NULL COMMENT 'url地址',
	private String url;
	// `is_menu` tinyint(4) DEFAULT NULL,
	private Integer is_menu;
	// `role_id` int(11) DEFAULT NULL,
	private Long role_id;
	// `include_url` tinyint(4) DEFAULT NULL,
	private Integer include_url;
	// `created_at` bigint(20) NOT NULL,
	private Long created_at;
	// `isdel` tinyint(4) NOT NULL DEFAULT '0',
	private Integer isdel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIs_menu() {
		return is_menu;
	}

	public void setIs_menu(Integer is_menu) {
		this.is_menu = is_menu;
	}

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public Integer getInclude_url() {
		return include_url;
	}

	public void setInclude_url(Integer include_url) {
		this.include_url = include_url;
	}

	public Long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Long created_at) {
		this.created_at = created_at;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

}
