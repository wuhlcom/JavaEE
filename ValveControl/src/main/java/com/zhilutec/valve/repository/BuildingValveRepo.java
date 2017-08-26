/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月25日 上午11:59:42 * 
*/
package com.zhilutec.valve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alibaba.fastjson.JSONArray;
import com.zhilutec.valve.bean.TblBuildingValve;

public interface BuildingValveRepo extends JpaRepository<TblBuildingValve, String> {
	@Query("select bv from TblBuildingValve bv where bv.comm_address in :comm_addresses")
	List<TblBuildingValve> getValves(@Param(value = "comm_addresses") JSONArray comm_addresses);
}
