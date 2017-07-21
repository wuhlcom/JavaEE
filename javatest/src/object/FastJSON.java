package ivyy.taobao.com.domain.fastjson;  
  
import ivyy.taobao.com.entity.Student;  
  
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
  
import com.alibaba.fastjson.JSON;  
import com.alibaba.fastjson.JSONObject;  
import com.alibaba.fastjson.TypeReference;  
  
/** 
 *@DEMO:json 
 *@Java：FastJSON.java 
 *@Date:2015-1-19上午10:28:12 
 *@Author:龙叔 
 *@Email:jilongliang@sina.com 
 *@Weibo:http://weibo.com/jilongliang 
 *@Version:1.0 
 *@Description：fastjson跟json-lib是语法很像，一句话说，所有json都差不多， 
 *大家伙也没不要研究那么多，懂一种自己最擅长而且熟悉能解决自己要解决的问题就OK， 
 *从fastjson反编译过来看 你就看到pom.xml里面的配置肯定能看到json-lib，此时能 
 *证明fastjson就是运用了json-lib！ 
 * 
 *-------------------------------------------- 
 *      <dependency> 
            <groupId>org.codehaus.jackson</groupId> 
            <artifactId>jackson-smile</artifactId> 
            <version>1.9.9</version> 
            <scope>test</scope> 
        </dependency> 
*-------------------------------------------- 
        <dependency> 
            <groupId>com.googlecode.json-simple</groupId> 
            <artifactId>json-simple</artifactId> 
            <version>1.1</version> 
            <scope>test</scope> 
        </dependency> 
-------------------------------------------- 
*        <dependency> 
            <groupId>net.sf.json-lib</groupId> 
            <artifactId>json-lib</artifactId> 
            <version>2.4</version> 
            <classifier>jdk15</classifier> 
            <scope>test</scope> 
        </dependency> 
-------------------------------------------- 
 */  
public class FastJSON {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) throws Exception{  
        //string2Json();  
        //string2Object();  
        //string2List();  
          
        map2json();  
        map2JSON();  
    }  
      
      
    /** 
     * 通过fastjson把字符串转换成JSON数据 
     * TypeReference 
     */  
    public static void string2Json(){  
        StringBuffer buffer=new StringBuffer();  
        buffer.append("{");  
            buffer.append("\"age\":").append("27").append(",");  
            buffer.append("\"userName\":").append("\"龙叔\"").append(",");  
            buffer.append("\"address\":").append("\"广东省云浮市\"");  
        buffer.append("}");  
          
        String jsonText=buffer.toString();  
          
        JSONObject jobj=JSON.parseObject(jsonText);  
        String address=jobj.get("address").toString();  
        System.out.println(address);  
    }  
      
      
    /** 
     * 通过fastjson把字符串转换成对象 
     * TypeReference 
     */  
    public static void string2Object(){  
        StringBuffer buffer=new StringBuffer();  
        buffer.append("{");  
            buffer.append("\"age\":").append("27").append(",");  
            buffer.append("\"userName\":").append("\"龙叔\"").append(",");  
            buffer.append("\"address\":").append("\"广东省云浮市\"");  
        buffer.append("}");  
          
        String jsonText=buffer.toString();  
        //方法一 把json字符串转成Student对象  
        Student stu1 = JSON.parseObject(jsonText, new TypeReference<Student>(){});  
        //方法二 把json字符串转成Student对象  
        Student stu2 = JSON.parseObject(jsonText,Student.class);    
          
        System.out.println(stu1.getAddress());  
        System.out.println(stu2.getAddress());  
    }  
      
    /** 
     * 通过fastjson把字符串转换成泛型数组 
     * TypeReference 
     */  
    public static void string2List(){  
        StringBuffer buffer=new StringBuffer();  
        buffer.append("[{");  
            buffer.append("\"age\":").append("27").append(",");  
            buffer.append("\"userName\":").append("\"龙叔\"").append(",");  
            buffer.append("\"address\":").append("\"广东省云浮市\"");  
        buffer.append("}]");  
          
        String jsonText=buffer.toString();  
        //转成成数组  
        Student[] stu2 = JSON.parseObject(jsonText,new TypeReference<Student[]>(){});    
        List<Student> list = Arrays.asList(stu2);  
          
        for(Student st:list){  
            System.out.println(st.getAddress());  
        }  
          
        // 转换成ArrayList  
        ArrayList<Student> list2 = JSON.parseObject(jsonText, new TypeReference<ArrayList<Student>>(){});   
          
        for (int i = 0; i < list2.size(); i++) {  
            Student obj =(Student) list2.get(i);  
            System.out.println(obj.getAddress());  
        }  
          
    }  
    /** 
     * 通过fastjson把Map换成字符串转 
     */  
    public static void map2json(){  
        //创建一个Map对象  
         Map<String,String> map = new HashMap<String, String>();  
         map.put("username", "周伯通");  
         map.put("address", "广东省仙游谷");  
         map.put("age", "198");  
         String json = JSON.toJSONString(map,true); //转成JSON数据  
           
         Map<String,String> map1 = (Map<String,String>)JSON.parse(json);   
         //遍历数组数据  
         for (String key : map1.keySet()) {   
            System.out.println(key+":"+map1.get(key));   
        }   
    }  
    /** 
     * 通过fastjson把Map换成字符串转 
     */  
    public static void map2JSON() {  
        Map map = new HashMap();  
        map.put("username", "周伯通");  
        map.put("address", "广东省仙游谷");  
        map.put("age", "198");  
        String json = JSON.toJSONString(map);  
        Map map1 = JSON.parseObject(json);  
        for (Object obj : map.entrySet()) {  
            Map.Entry<String, String> entry = (Map.Entry<String, String>) obj;  
            System.out.println(entry.getKey() + "--->" + entry.getValue());  
        }  
    }   
}  


[java] view plain copy print?
package ivyy.taobao.com.entity;  
  
import java.io.Serializable;  
  
/** 
 *@Author:liangjl 
 *@Date:2014-12-19 
 *@Version:1.0 
 *@Description: 
 */  
public class Student implements Serializable{  
    private Integer age;  
    private String sex;  
    private String userName;  
    private String birthday;  
    private String address;  
    private String email;  
      
    public Integer getAge() {  
        return age;  
    }  
    public void setAge(Integer age) {  
        this.age = age;  
    }  
    public String getSex() {  
        return sex;  
    }  
    public void setSex(String sex) {  
        this.sex = sex;  
    }  
    public String getUserName() {  
        return userName;  
    }  
    public void setUserName(String userName) {  
        this.userName = userName;  
    }  
    public String getBirthday() {  
        return birthday;  
    }  
    public void setBirthday(String birthday) {  
        this.birthday = birthday;  
    }  
    public String getAddress() {  
        return address;  
    }  
    public void setAddress(String address) {  
        this.address = address;  
    }  
    public String getEmail() {  
        return email;  
    }  
    public void setEmail(String email) {  
        this.email = email;  
    }  
}  