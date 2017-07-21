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
 *@Java��FastJSON.java 
 *@Date:2015-1-19����10:28:12 
 *@Author:���� 
 *@Email:jilongliang@sina.com 
 *@Weibo:http://weibo.com/jilongliang 
 *@Version:1.0 
 *@Description��fastjson��json-lib���﷨����һ�仰˵������json����࣬ 
 *��һ�Ҳû��Ҫ�о���ô�࣬��һ���Լ����ó�������Ϥ�ܽ���Լ�Ҫ����������OK�� 
 *��fastjson����������� ��Ϳ���pom.xml��������ÿ϶��ܿ���json-lib����ʱ�� 
 *֤��fastjson����������json-lib�� 
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
     * ͨ��fastjson���ַ���ת����JSON���� 
     * TypeReference 
     */  
    public static void string2Json(){  
        StringBuffer buffer=new StringBuffer();  
        buffer.append("{");  
            buffer.append("\"age\":").append("27").append(",");  
            buffer.append("\"userName\":").append("\"����\"").append(",");  
            buffer.append("\"address\":").append("\"�㶫ʡ�Ƹ���\"");  
        buffer.append("}");  
          
        String jsonText=buffer.toString();  
          
        JSONObject jobj=JSON.parseObject(jsonText);  
        String address=jobj.get("address").toString();  
        System.out.println(address);  
    }  
      
      
    /** 
     * ͨ��fastjson���ַ���ת���ɶ��� 
     * TypeReference 
     */  
    public static void string2Object(){  
        StringBuffer buffer=new StringBuffer();  
        buffer.append("{");  
            buffer.append("\"age\":").append("27").append(",");  
            buffer.append("\"userName\":").append("\"����\"").append(",");  
            buffer.append("\"address\":").append("\"�㶫ʡ�Ƹ���\"");  
        buffer.append("}");  
          
        String jsonText=buffer.toString();  
        //����һ ��json�ַ���ת��Student����  
        Student stu1 = JSON.parseObject(jsonText, new TypeReference<Student>(){});  
        //������ ��json�ַ���ת��Student����  
        Student stu2 = JSON.parseObject(jsonText,Student.class);    
          
        System.out.println(stu1.getAddress());  
        System.out.println(stu2.getAddress());  
    }  
      
    /** 
     * ͨ��fastjson���ַ���ת���ɷ������� 
     * TypeReference 
     */  
    public static void string2List(){  
        StringBuffer buffer=new StringBuffer();  
        buffer.append("[{");  
            buffer.append("\"age\":").append("27").append(",");  
            buffer.append("\"userName\":").append("\"����\"").append(",");  
            buffer.append("\"address\":").append("\"�㶫ʡ�Ƹ���\"");  
        buffer.append("}]");  
          
        String jsonText=buffer.toString();  
        //ת�ɳ�����  
        Student[] stu2 = JSON.parseObject(jsonText,new TypeReference<Student[]>(){});    
        List<Student> list = Arrays.asList(stu2);  
          
        for(Student st:list){  
            System.out.println(st.getAddress());  
        }  
          
        // ת����ArrayList  
        ArrayList<Student> list2 = JSON.parseObject(jsonText, new TypeReference<ArrayList<Student>>(){});   
          
        for (int i = 0; i < list2.size(); i++) {  
            Student obj =(Student) list2.get(i);  
            System.out.println(obj.getAddress());  
        }  
          
    }  
    /** 
     * ͨ��fastjson��Map�����ַ���ת 
     */  
    public static void map2json(){  
        //����һ��Map����  
         Map<String,String> map = new HashMap<String, String>();  
         map.put("username", "�ܲ�ͨ");  
         map.put("address", "�㶫ʡ���ι�");  
         map.put("age", "198");  
         String json = JSON.toJSONString(map,true); //ת��JSON����  
           
         Map<String,String> map1 = (Map<String,String>)JSON.parse(json);   
         //������������  
         for (String key : map1.keySet()) {   
            System.out.println(key+":"+map1.get(key));   
        }   
    }  
    /** 
     * ͨ��fastjson��Map�����ַ���ת 
     */  
    public static void map2JSON() {  
        Map map = new HashMap();  
        map.put("username", "�ܲ�ͨ");  
        map.put("address", "�㶫ʡ���ι�");  
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