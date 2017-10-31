package fish.yu.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * @author yuliang-ds1
 * 系统用户表
 */
@TableName("t_user")
public class User extends Model<User> {
	
	private static final long serialVersionUID = 1L;
	
	@TableId(type=IdType.AUTO)
	private String id;//用户唯一标示
	
	private String nickName;//用户昵称
	
	@TableField("pswd")
	private String password;//用户密码
	
	
	private String salt; //密码随机盐
	
	
	private String email;//用户邮箱
	
	@TableField("create_time")
	private Date   createTime;//用户创建时间
	
	@TableField("last_login_time")
	private Date   lastLoginTime;//用户最后登录时间
	
	
	private Integer status;//用户账号状态   是否可用 1标识可用 0标识禁止
	
	
    public User(){}
	
	public User(User user) {
		super();
		this.id = user.getId();
		this.nickName = user.getNickName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.createTime = user.getCreateTime();
		this.lastLoginTime = user.getLastLoginTime();
		this.status = user.getStatus();
		this.salt=user.getSalt();
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}


	public String getSalt() {
		return salt;
	}



	public void setSalt(String salt) {
		this.salt = salt;
	}



	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getLastLoginTime() {
		return lastLoginTime;
	}


	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		
		return this.id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "User打印 nickname:"+nickName+"   email:"+email+"   createTime:"+createTime+"  lastLoginTime"+lastLoginTime+"  status:"+status;
	}

}
