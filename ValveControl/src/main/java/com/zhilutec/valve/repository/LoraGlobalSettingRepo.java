/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月17日 上午11:05:00 * 
*/
package com.zhilutec.valve.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilutec.valve.bean.models.TblLoraGlobalSetting;

public interface LoraGlobalSettingRepo extends JpaRepository<TblLoraGlobalSetting, Integer> {
	

}
