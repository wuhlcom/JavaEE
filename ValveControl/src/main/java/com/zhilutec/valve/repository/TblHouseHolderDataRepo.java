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
import com.zhilutec.valve.bean.TblValveHouseHolderData;
import com.zhilutec.valve.bean.mapper.Stealing;

public interface TblHouseHolderDataRepo
		extends JpaRepository<TblValveHouseHolderData, String>, JpaSpecificationExecutor<TblValveHouseHolderData> {

//	String stealingJPQL = "select new TblValveHouseHolderData(v.house_code,v.comm_address,v.collect_time,v.supply_temp,v.return_temp,v.valve_state)"
//			+ "from TblValveHouseHolderData v "
//			+ "where (v.collect_time >= :start_time and v.collect_time<= :end_time) "
//			+ "and (v.supply_temp >= :wit_min and v.supply_temp <= :wit_max ) "
//			+ "and (v.return_temp >=:wot_min and v.return_temp <=:wot_max )	"
//			+ "and (ABS(v.return_temp-v.supply_temp)< :temdif)";
//
//	@Query(value = queryStealingSql)
//	List<TblValveHouseHolderData> findStealingJPQL(@Param("start_time") Long start_time,
//			@Param("end_time") Long end_time, @Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max,
//			@Param("wot_min") Double wot_min, @Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	// String queryStealingSql = "select
	// house_code,comm_address,collect_time,supply_temp, return_temp,
	// valve_state from zl_householder"
	// + "where (collect_time >= 1502939922 and collect_time<=1502939972)"
	// + "and (supply_temp >=-10 and supply_temp <=10 )"
	// + "and (return_temp >=20 and return_temp <=25 ) and
	// (ABS(return_temp-supply_temp)<30)";
	//
	String queryStealingSql = "select house_code,comm_address,collect_time,supply_temp, return_temp, valve_state "
			+ "from zl_householder_data " + "where (collect_time >= :start_time and collect_time<= :end_time) "
			+ "and (supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "and (ABS(return_temp-supply_temp)< :temdif)";

	@Query(value = queryStealingSql, nativeQuery = true)
	List<Object[]> findStealing(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	String queryStealingSql2Or = "select house_code,comm_address,collect_time,supply_temp, return_temp, valve_state "
			+ "from zl_householder_data " + "where (collect_time >= :start_time and collect_time<= :end_time) "
			+ "and (supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "or (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "or (ABS(return_temp-supply_temp)< :temdif)";

	@Query(value = queryStealingSql2Or, nativeQuery = true)
	List<Object[]> findStealing2Or(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	String queryStealingSqlOrAnd = "select house_code,comm_address,collect_time,supply_temp, return_temp, valve_state "
			+ "from zl_householder_data " + "where (collect_time >= :start_time and collect_time<= :end_time) "
			+ "and (supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "or (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "and (ABS(return_temp-supply_temp)< :temdif)";

	@Query(value = queryStealingSqlOrAnd, nativeQuery = true)
	List<Object[]> findStealingOrAnd(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	String queryStealingSqlAndOr = "select house_code,comm_address,collect_time,supply_temp, return_temp, valve_state "
			+ "from zl_householder_data " + "where (collect_time >= :start_time and collect_time<= :end_time) "
			+ "and (supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "or (ABS(return_temp-supply_temp)< :temdif)";

	@Query(value = queryStealingSqlOrAnd, nativeQuery = true)
	List<Object[]> findStealingAndOr(@Param("start_time") Long start_time, @Param("end_time") Long end_time,
			@Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max, @Param("wot_min") Double wot_min,
			@Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	// select comm_type,comm_address
	// from zl_householder_data
	// where (collect_time >= 1502939922 and collect_time<=1502949972)
	// and (supply_temp >=-10 and supply_temp <=20 )
	// and (return_temp >=10 and return_temp <=25 )
	// and (ABS(return_temp-supply_temp)<30)
	// group by comm_type,comm_address

	String queryStealingGroupSql = "select comm_type,comm_address " 
	+ "from zl_householder_data "
			+ "where (collect_time >= :start_time and collect_time<= :end_time) "
			+ "and (supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "and (ABS(return_temp-supply_temp)< :temdif) " + "group by comm_type,comm_address";

	@Query(value = queryStealingGroupSql, nativeQuery = true)
	List<Object[]> findStealingGroup(@Param("start_time") Long start_time,
			@Param("end_time") Long end_time, @Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max,
			@Param("wot_min") Double wot_min, @Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	String queryStealingGroupSql2Or = "select comm_type,comm_address " 
			+ "from zl_householder_data "
			+ "where (collect_time >= :start_time and collect_time<= :end_time) "
			+ "and (supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "or (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "or (ABS(return_temp-supply_temp)< :temdif) " + "group by comm_type,comm_address";

	@Query(value = queryStealingGroupSql2Or, nativeQuery = true)
	List<Object[]> findStealingGroup2Or(@Param("start_time") Long start_time,
			@Param("end_time") Long end_time, @Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max,
			@Param("wot_min") Double wot_min, @Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	String queryStealingGroupSqlAndOr = "select comm_type,comm_address " 
			+ "from zl_householder_data "
			+ "where (collect_time >= :start_time and collect_time<= :end_time) "
			+ "and (supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "or (ABS(return_temp-supply_temp)< :temdif) " + "group by comm_type,comm_address";

	@Query(value = queryStealingGroupSqlAndOr, nativeQuery = true)
	List<Object[]> findStealingGroupAndOr(@Param("start_time") Long start_time,
			@Param("end_time") Long end_time, @Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max,
			@Param("wot_min") Double wot_min, @Param("wot_max") Double wot_max, @Param("temdif") Double temdif);

	String queryStealingGroupSqlOrAnd = "select comm_type,comm_address " 
			+ "from zl_householder_data "
			+ "where (collect_time >= :start_time and collect_time<= :end_time) "
			+ "and (supply_temp >= :wit_min and supply_temp <= :wit_max ) "
			+ "and (return_temp >=:wot_min and return_temp <=:wot_max )	"
			+ "and (ABS(return_temp-supply_temp)< :temdif) " + "group by comm_type,comm_address";

	@Query(value = queryStealingGroupSqlOrAnd, nativeQuery = true)
	List<Object[]> findStealingGroupOrAnd(@Param("start_time") Long start_time,
			@Param("end_time") Long end_time, @Param("wit_min") Double wit_min, @Param("wit_max") Double wit_max,
			@Param("wot_min") Double wot_min, @Param("wot_max") Double wot_max, @Param("temdif") Double temdif);
}
