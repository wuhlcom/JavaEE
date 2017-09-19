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

import com.zhilutec.valve.bean.models.TblHouseHolderData;

public interface HouseHolderDataRepo
		extends JpaRepository<TblHouseHolderData, String>, JpaSpecificationExecutor<TblHouseHolderData> {

	//可疑盗热分析
	// and and  //时间和进水温度，回水温，瀑差
	String queryStealingSql = "select comm_address,collect_time,supply_temp, return_temp, valve_state "
			+ "from zl_householder_data " 
			+ "where (collect_time >= :start_time and collect_time<= :end_time and valve_state=0) "
			+ "and ((supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "and (ABS(return_temp-supply_temp)< :temdif))";

	@Query(value = queryStealingSql, nativeQuery = true)
	List<Object[]> findStealing(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	// or or
	String queryStealingSql2Or = "select comm_address,collect_time,supply_temp, return_temp, valve_state "
			+ "from zl_householder_data " 
			+ "where (collect_time >= :start_time and collect_time<= :end_time) "
			+ "and ((supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "or (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "or (ABS(return_temp-supply_temp)< :temdif))";

	@Query(value = queryStealingSql2Or, nativeQuery = true)
	List<Object[]> findStealing2Or(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	// or and
	String queryStealingSqlOrAnd = "select comm_address,collect_time,supply_temp, return_temp, valve_state "
			+ "from zl_householder_data " 
			+ "where (collect_time >= :start_time and collect_time<= :end_time and valve_state=0) "
			+ "and ((supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "or (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "and (ABS(return_temp-supply_temp)< :temdif))";

	@Query(value = queryStealingSqlOrAnd, nativeQuery = true)
	List<Object[]> findStealingOrAnd(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	// and or
	String queryStealingSqlAndOr = "select comm_address,collect_time,supply_temp, return_temp, valve_state "
			+ "from zl_householder_data " 
			+ "where (collect_time >= :start_time and collect_time<= :end_time and valve_state=0) "
			+ "and ((supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "or (ABS(return_temp-supply_temp)< :temdif))";

	@Query(value = queryStealingSqlAndOr, nativeQuery = true)
	List<Object[]> findStealingAndOr(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	// 仅按时间查询
	String queryStealingSqlByTime = "select comm_address,collect_time,supply_temp, return_temp, valve_state "
			+ "from zl_householder_data " 
			+ "where (collect_time >= :start_time and collect_time<= :end_time and valve_state=0)";
	@Query(value = queryStealingSqlByTime, nativeQuery = true)
	List<Object[]> findStealingByTime(@Param("start_time") Long start_time, @Param("end_time") Long end_time);
	
		
	//可疑盗热住户
	//and and
	String queryStealingGroupSql = "select comm_type,comm_address " + "from zl_householder_data "
			+ "where (collect_time >= :start_time and collect_time<= :end_time and valve_state=0) "
			+ "and ((supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "and (ABS(return_temp-supply_temp)< :temdif)) " 
			+ "group by comm_type,comm_address";

	@Query(value = queryStealingGroupSql, nativeQuery = true)
	List<Object[]> findStealingGroup(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	//or or
	String queryStealingGroupSql2Or = "select comm_type,comm_address " + "from zl_householder_data "
			+ "where (collect_time >= :start_time and collect_time<= :end_time and valve_state=0) "
			+ "and ((supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "or (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "or (ABS(return_temp-supply_temp)< :temdif)) " 
			+ "group by comm_type,comm_address";

	@Query(value = queryStealingGroupSql2Or, nativeQuery = true)
	List<Object[]> findStealingGroup2Or(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	//and or
	String queryStealingGroupSqlAndOr = "select comm_type,comm_address " + "from zl_householder_data "
			+ "where (collect_time >= :start_time and collect_time<= :end_time and valve_state=0) "
			+ "and ((supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "or (ABS(return_temp-supply_temp)< :temdif)) " 
			+ "group by comm_type,comm_address";

	@Query(value = queryStealingGroupSqlAndOr, nativeQuery = true)
	List<Object[]> findStealingGroupAndOr(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	//or and
	String queryStealingGroupSqlOrAnd = "select comm_type,comm_address " + "from zl_householder_data "
			+ "where (collect_time >= :start_time and collect_time<= :end_time and valve_state=0) "
			+ "and ((supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "and (ABS(return_temp-supply_temp)< :temdif)) " 
			+ "group by comm_type,comm_address";

	@Query(value = queryStealingGroupSqlOrAnd, nativeQuery = true)
	List<Object[]> findStealingGroupOrAnd(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);
	
	//按时间查询
	String queryStealingGroupTime= "select comm_type,comm_address " 
	+ "from zl_householder_data "
	+ "where (collect_time >= :start_time and collect_time<= :end_time and valve_state=0) "
	+ "group by comm_type,comm_address";
	@Query(value = queryStealingGroupTime, nativeQuery = true)
	List<Object[]> findStealingGroupByTime(@Param("start_time") Long start_time, @Param("end_time") Long end_time);
}
