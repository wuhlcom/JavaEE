/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月25日 上午9:48:26 * 
*/
package com.zhilutec.valve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alibaba.fastjson.JSONArray;
import com.zhilutec.valve.bean.models.TblBuildingCalorimeter;

public interface BuildingCalorimeterRepo
		extends JpaRepository<TblBuildingCalorimeter, String>, JpaSpecificationExecutor<TblBuildingCalorimeter> {
	@Query("select bc from TblBuildingCalorimeter bc where bc.comm_address in :comm_addresses")
	List<TblBuildingCalorimeter> getCalorimeters(@Param(value = "comm_addresses") JSONArray comm_addresses);
}
