/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月28日 下午1:50:54 * 
*/
package com.dazk.db.model;

public class DataPermission {
	// `id` int(11) NOT NULL AUTO_INCREMENT,
	private Long id;

	// `user_id` int(11) NOT NULL COMMENT '账号ID',
	private Long user_id;

	// `company_code` varchar(32) NOT NULL COMMENT '热力公司code',
	private String company_code;

	// `hotstation_code` varchar(32) NOT NULL COMMENT '热站code',
	private String hotstation_code;

	// `community_code` varchar(32) NOT NULL COMMENT '小区code',
	private String community_code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getHotstation_code() {
		return hotstation_code;
	}

	public void setHotstation_code(String hotstation_code) {
		this.hotstation_code = hotstation_code;
	}

	public String getCommunity_code() {
		return community_code;
	}

	public void setCommunity_code(String community_code) {
		this.community_code = community_code;
	}

}
