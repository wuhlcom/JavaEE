/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月20日 下午3:43:52 * 
*/ 
package object;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTest {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		//java.sql.Date 只存储日期数据不存储时间数据 
//	     // 会丢失时间数据 
//	     preparedStatement.setDate(1, new java.sql.Date(date.getTime())); 
//	     //可以这样来处理 
//	     preparedStatement.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
		
timestamp2str();
		
str2timestamp();
		date2timestamp();
		
		//解析数据库中时间
		SimpleDateFormat simpleTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义日期格式  默认时间格式：yyyy-MM-dd HH:mm:ss  
		//SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");  
//		String passTime = pwoEditForm.getPasstime();  //读取表中时间
//		java.util.Date passUtilDate = simpleTime.parse(passTime);  //格式化时
//		java.sql.TimeStamp passSqlDate = new java.sql.TimeStamp(passUtilDate.getTime());  //转成timestamp
//		pwOrder.setPasstime(passSqlDate);  //写入数据库
		
//		要求java实体类定义时，也要将相关字段定义为java.sql.TimeStamp类型即可，当然hibernate对应的type也要写成timesta
		logTimestamp();
	}

	/**  
	* @Title: date2timestamp  
	* @Description: TODO 
	* @param    
	* @return void   
	* @throws  
	*/
	private static void date2timestamp() {
		System.out.println("----------------------");
		Date time1 = new Date(); 
		 System.out.println(time1);
		 System.out.println(time1.getTime());
		Timestamp passSqlDate = new Timestamp(time1.getTime());
		System.out.println(passSqlDate);
		System.out.println("----------------------");
	}

	/**  
	* @Title: logTimestamp  
	* @Description: TODO 
	* @param    
	* @return void   
	* @throws  
	*/
	private static void logTimestamp() {
		long currTime = System.currentTimeMillis();  
		Timestamp timeObj = new Timestamp(currTime);            //yyyy-MM-dd HH:mm:ss:mis  
		//System.out.println("timeObj----->"+timeObj);          //timeObj----->2012-02-09 14:56:55.401  
		String gjsj = timeObj.toString().substring(0,19);       //yyyy-MM-dd HH:mm:ss  
		//System.out.println("gjsj----->"+gjsj);                //gjsj----->2012-02-09 14:56:55 
	}

	/**  
	* @Title: timestamp2str  
	* @Description: TODO 
	* @param    
	* @return void   
	* @throws  
	*/
	private static void timestamp2str() {
		//		用Timestamp来记录日期时间还是很方便的，但有时候显示的时候是不需要小数位后面的毫秒的，
		//		这样就需要在转换为String时重新定义格式。
		//		 Timestamp转化为String: 
				System.out.println("-----------Timestamp转化为String:-----------");
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				  
				//定义格式，不显示毫秒   		    
				Timestamp now = new Timestamp(System.currentTimeMillis());   
				System.out.println(now);
				  
				//获取系统当前时间  	  
				String str = df.format(now);   
				System.out.println(str);
	}

	/**  
	* @Title: str2Timestamp  
	* @Description: TODO 
	* @param    
	* @return void   
	* @throws  
	*/
	private static void str2timestamp() {
		//      String转化为Timestamp: 
				System.out.println("-----------String转化为Timestamp:-----------");
				SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
				  
				String time = df1.format(new Date());  	 
				System.out.println(time);	
				Timestamp ts = Timestamp.valueOf(time);  
				System.out.println(ts);
	}

}
