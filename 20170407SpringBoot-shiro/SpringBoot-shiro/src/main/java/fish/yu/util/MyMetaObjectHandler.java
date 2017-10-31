package fish.yu.util;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.mapper.IMetaObjectHandler;

/**
 * 
 * @author yuliang-ds1
 * mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 */
public class MyMetaObjectHandler implements IMetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		
		try{
			Object last_update_date = metaObject.getValue("lastUpdateDate");
			if (null == last_update_date) {
				metaObject.setValue("lastUpdateDate", new Date());
			}
		}catch(Exception e){
			System.out.println("IMetaObjectHandler-MetaObject"+metaObject+" message:"+e.getMessage());
		}
		/*Object last_update_date = metaObject.getValue("lastUpdateDate");
		if (null == last_update_date) {
			metaObject.setValue("lastUpdateDate", new Date());
		}*/
	}
}
