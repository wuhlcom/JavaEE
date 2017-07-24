/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月21日 上午10:19:21 * 
*/
package com.zhilu.device.bean;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tbl_iot_product_info")
@Entity
public class TblIotProductInfo {
	// `id` varchar(16) NOT NULL COMMENT '索引',
	@Id
	private String id;
	// `name` varchar(255) DEFAULT NULL COMMENT '产品名称',
	private String name;
	// `type` varchar(255) DEFAULT NULL COMMENT '产品类型',
	private String type;
	// `board_id` varchar(16) DEFAULT NULL COMMENT '模块类型',
	private String boardId;
	// `diagram` varchar(256) DEFAULT NULL COMMENT '示意图',
	private String diagram;
	// `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
	private String createUser;
	// `modify_user` varchar(64) DEFAULT NULL COMMENT '修改用户',
	private String modifyUser;
	// `create_time` datetime DEFAULT NULL COMMENT '创建时间',
	private Timestamp create_time;
	// `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
	private Timestamp modify_time;
	// `description` varchar(256) DEFAULT NULL COMMENT '描述',
	private String description;
	// `category` varchar(255) DEFAULT NULL COMMENT '产品类别',
	private String category;
	// `encoding` tinyint(1) DEFAULT '0' COMMENT '编码方式 0：json,1:tlv',
	private Integer encoding;
	// `productconf` varchar(565) DEFAULT NULL COMMENT '产品配置',
	private String productconf;
	// `publish` tinyint(1) DEFAULT '0' COMMENT '是否发布0：否,1:是（审核中）,2:审核通过',
	private Integer publish;
	// `crt` varchar(256) DEFAULT NULL COMMENT '产品crt证书路径',
	private String crt;
	// `key` varchar(256) DEFAULT NULL COMMENT '产品key证书路径',
	private String key;
	// `sdk` varchar(256) DEFAULT NULL COMMENT 'sdk文件路径',
	private String sdk;
	// `link_interval` int(5) DEFAULT '120' COMMENT '链路心跳间隔',
	private Long link_interval;
	// `data_eporting_interval` int(5) DEFAULT '120' COMMENT '数据上报间隔时间',
	private Long data_eporting_interval;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getDiagram() {
		return diagram;
	}

	public void setDiagram(String diagram) {
		this.diagram = diagram;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TblIotProductInfo [id=" + id + ", name=" + name + ", type=" + type + ", boardId=" + boardId
				+ ", diagram=" + diagram + ", createUser=" + createUser + ", modifyUser=" + modifyUser
				+ ", create_time=" + create_time + ", modify_time=" + modify_time + ", description=" + description
				+ ", category=" + category + ", encoding=" + encoding + ", productconf=" + productconf + ", publish="
				+ publish + ", crt=" + crt + ", key=" + key + ", sdk=" + sdk + ", link_interval=" + link_interval
				+ ", data_eporting_interval=" + data_eporting_interval + "]";
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getModify_time() {
		return modify_time;
	}

	public void setModify_time(Timestamp modify_time) {
		this.modify_time = modify_time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getEncoding() {
		return encoding;
	}

	public void setEncoding(Integer encoding) {
		this.encoding = encoding;
	}

	public String getProductconf() {
		return productconf;
	}

	public void setProductconf(String productconf) {
		this.productconf = productconf;
	}

	public Integer getPublish() {
		return publish;
	}

	public void setPublish(Integer publish) {
		this.publish = publish;
	}

	public String getCrt() {
		return crt;
	}

	public void setCrt(String crt) {
		this.crt = crt;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSdk() {
		return sdk;
	}

	public void setSdk(String sdk) {
		this.sdk = sdk;
	}

	public Long getLink_interval() {
		return link_interval;
	}

	public void setLink_interval(Long link_interval) {
		this.link_interval = link_interval;
	}

	public Long getData_eporting_interval() {
		return data_eporting_interval;
	}

	public void setData_eporting_interval(Long data_eporting_interval) {
		this.data_eporting_interval = data_eporting_interval;
	}

}
