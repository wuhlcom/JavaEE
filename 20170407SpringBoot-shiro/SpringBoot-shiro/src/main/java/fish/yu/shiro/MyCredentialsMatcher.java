package fish.yu.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import fish.yu.entity.User;

public class MyCredentialsMatcher  extends SimpleCredentialsMatcher {
	
	//加密算法名称
	public static final String  HASH_A_NAME="MD5";
	
	public static final Integer  HASH_A_NAME_NUM=2;
	
	 @Override  
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {  
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken; 
        //对用户输入的密码进行加密
        SimpleAuthenticationInfo sp=(SimpleAuthenticationInfo)info;
        ByteSource credentialsSalt = sp.getCredentialsSalt();
        
        Object tokenCredentials = encrypt(credentialsSalt,String.valueOf(token.getPassword())); 
        System.out.println("加密密文:"+tokenCredentials.toString());
        
        //获取数据库中的密码
        Object accountCredentials = getCredentials(info);
       
        Object credentials = sp.getCredentials();
        PrincipalCollection principals = sp.getPrincipals();
        System.err.println();
        
        System.out.println("查询出来的数据密码："+accountCredentials);
       
        
        
       
        
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        System.out.println("密码比对结果："+tokenCredentials.toString().equals(accountCredentials.toString()));
        return tokenCredentials.toString().equals(accountCredentials.toString());  
    }  
  
    //将传进来密码加密方法  
    private String encrypt(ByteSource  credentialsSalt, String password) {  
        Object simpleHash = new SimpleHash(HASH_A_NAME, password,  
        		credentialsSalt, HASH_A_NAME_NUM); 
       /* String sha384Hex = new Sha384Hash(data).toBase64();  
        System.out.println(data + ":" + sha384Hex);  
        return sha384Hex; */ 
    	return simpleHash.toString();
    	
    }
	
	
    public static void main(String[] args) {
    	
    	 int hashIterations = 2;//加密的次数  
         Object credentials = "123456";//密码  
         String hashAlgorithmName = "MD5";//加密方式  
         Object simpleHash = new SimpleHash(hashAlgorithmName, credentials,  
        		 ByteSource.Util.bytes("adminCDES"), hashIterations);
         
         System.out.println("盐"+ByteSource.Util.bytes("adminCDES"));
         //YWRtaW5DREVT
         //YWRtaW5DREVT
         System.out.println("加密后的值----->" + simpleHash);  
         //77cdad23175c6b21efb7a3dda82ff7b4
         //77cdad23175c6b21efb7a3dda82ff7b4
		
	}

}
