package fish.yu.shiro;

import java.io.Serializable;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 
 * @author yuliang-ds1
 * 用户认证令牌
 */
public class ShiroToken  extends  UsernamePasswordToken implements Serializable{
	
	private static final long serialVersionUID = -6451794657814516274L;
	
	/*父类是char[] 类型的，实际用户密码是String*/
	private  String pswd;

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	

}
