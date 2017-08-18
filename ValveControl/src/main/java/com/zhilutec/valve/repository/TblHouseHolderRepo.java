/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月17日 上午11:05:00 * 
*/
package com.zhilutec.valve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhilutec.valve.bean.TblValveHouseHolder;

public interface TblHouseHolderRepo
		extends JpaRepository<TblValveHouseHolder, String>, JpaSpecificationExecutor<TblValveHouseHolder> {

//	String queryStealingSql = "select id,house_code,comm_address,collect_time,supply_temp, return_temp, valve_state "
//			+ "from zl_householder "
//			+ "where (collect_time >= :start_time and collect_time<= :end_time) "
//			+ "and (supply_temp >= :wit_min and supply_temp <= :wit_max ) "
//			+ "condition1 (return_temp >=:wot_min and return_temp <=:wot_max )	"
//			+ ":condition2 (ABS(return_temp-supply_temp)< :temdif) ";
//
//	@Query(value = queryStealingSql, nativeQuery = true)
//	List<TblValveHouseHolder> queryStealing(
//			@Param("start_time") Long start_time,
//			@Param("end_time") Long end_time,			
//			@Param("wit_min") Double wit_min,
//			@Param("wit_max") Double wit_max,
//			@Param("condition1") String condition1,
//			@Param("wot_min") Double wot_min,
//			@Param("wot_max") Double wot_max,
//			@Param("condition2") String condition2,
//			@Param("temdif") Integer temdif
//			);
}
