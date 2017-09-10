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
public class Menu extends BaseEntity implements Comparable<Menu> {
	// `id` int(11) NOT NULL AUTO_INCREMENT,
	private Long id;
	// `name` varchar(20) NOT NULL COMMENT '名称',
	private String name;
	// `code` varchar(20) NOT NULL COMMENT '编号',
	private String code;
	// `uri` varchar(45) NOT NULL COMMENT 'uri地址',
	private String uri;
	// `is_menu` tinyint(4) DEFAULT NULL,
	private Integer is_menu=0;
	// `lv` int(11) DEFAULT NULL,
	private Long lv;
	private Long role_id;
	// `include_url` tinyint(4) DEFAULT NULL,
	private Integer include_url;
	// `created_at` bigint(20) NOT NULL,
	private Long created_at;
	// `isdel` tinyint(4) NOT NULL DEFAULT '0',
	private Integer isdel;
	
	
	
	/**
	 * 
	 */
	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}
	
		

	/**
	 * @param id
	 * @param name
	 * @param code
	 * @param uri
	 * @param is_menu
	 * @param lv
	 * @param role_id
	 * @param include_url
	 * @param created_at
	 * @param isdel
	 * @param parent_id
	 * @param front_router
	 */
	public Menu(Long id, String name, String code, String uri, Integer is_menu, Long lv, Long role_id,
			Integer include_url, Long created_at, Integer isdel, Long parent_id, String front_router) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.uri = uri;
		this.is_menu = is_menu;
		this.lv = lv;
		this.role_id = role_id;
		this.include_url = include_url;
		this.created_at = created_at;
		this.isdel = isdel;
		this.parent_id = parent_id;
		this.front_router = front_router;
	}

	 public int compareTo(Menu menu) {
	        if (this.id > menu.getId()) {
	            return 1;
	        } else {
	            return -1;
	        }
	    }

	// parent
	private Long parent_id=0L;
	
	private String front_router;

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

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getIs_menu() {
		return is_menu;
	}

	public void setIs_menu(Integer is_menu) {
		this.is_menu = is_menu;
	}

	public Long getLv() {
		return lv;
	}

	public void setLv(Long lv) {
		this.lv = lv;
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

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public String getFront_router() {
		return front_router;
	}

	public void setFront_router(String front_router) {
		this.front_router = front_router;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", is_menu=" + is_menu + ", include_url=" + include_url
				+ ", parent_id=" + parent_id + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((created_at == null) ? 0 : created_at.hashCode());
		result = prime * result + ((front_router == null) ? 0 : front_router.hashCode());
		result = prime * result + ((include_url == null) ? 0 : include_url.hashCode());
		result = prime * result + ((is_menu == null) ? 0 : is_menu.hashCode());
		result = prime * result + ((isdel == null) ? 0 : isdel.hashCode());
		result = prime * result + ((lv == null) ? 0 : lv.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent_id == null) ? 0 : parent_id.hashCode());
		result = prime * result + ((role_id == null) ? 0 : role_id.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (created_at == null) {
			if (other.created_at != null)
				return false;
		} else if (!created_at.equals(other.created_at))
			return false;
		if (front_router == null) {
			if (other.front_router != null)
				return false;
		} else if (!front_router.equals(other.front_router))
			return false;
		if (include_url == null) {
			if (other.include_url != null)
				return false;
		} else if (!include_url.equals(other.include_url))
			return false;
		if (is_menu == null) {
			if (other.is_menu != null)
				return false;
		} else if (!is_menu.equals(other.is_menu))
			return false;
		if (isdel == null) {
			if (other.isdel != null)
				return false;
		} else if (!isdel.equals(other.isdel))
			return false;
		if (lv == null) {
			if (other.lv != null)
				return false;
		} else if (!lv.equals(other.lv))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent_id == null) {
			if (other.parent_id != null)
				return false;
		} else if (!parent_id.equals(other.parent_id))
			return false;
		if (role_id == null) {
			if (other.role_id != null)
				return false;
		} else if (!role_id.equals(other.role_id))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
	
	

}
