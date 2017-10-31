package fish.yu.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.activerecord.Model;

/**
 * 
 * @author yuliang-ds1
 * 角色集合and资源集合
 */

public class RolesAndPermissions  {
	
	private static final long serialVersionUID = 1L;
	
	private  String rid;//角色Id集合
	
	private  String rname;//角色名称
	
	private  String rtype;//角色类型
	
	private  String pid;//权限id集合
	
	private  String pname;//资源name
	
	private  String purl;//资源url

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public String getRtype() {
		return rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
	
}
