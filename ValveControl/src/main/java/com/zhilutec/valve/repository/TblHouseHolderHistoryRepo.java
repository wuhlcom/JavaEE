package com.zhilutec.valve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhilutec.valve.bean.TblHouseHolderHistory;

public interface TblHouseHolderHistoryRepo
	extends JpaRepository<TblHouseHolderHistory, String>, JpaSpecificationExecutor<TblHouseHolderHistory> {
	
	@Query(value = "select * from zl_householder_data where "
			+ "comm_address = :comm_address and (collect_time >= :startTime and collect_time <= :endTime)  "
			+ "limit :start,:pages", nativeQuery = true)
	List<TblHouseHolderHistory> getPageDataById(@Param("comm_address") String commAddress, 
			@Param("startTime") int startTime, @Param("endTime") int endTime,
			@Param("start") int start, @Param("pages") int pages);
	
	@Query(value = "select * from zl_householder_data where collect_time >= :startTime and collect_time <= :endTime "
			+ "order by collect_time desc, comm_address desc  "
			+ "limit :start,:pages", nativeQuery = true)
	List<TblHouseHolderHistory> getAllData(@Param("startTime") int startTime, @Param("endTime") int endTime,
			@Param("start") int start, @Param("pages") int pages);
	
	@Query(value = "select count(1) from zl_householder_data where "
			+ "comm_address = :comm_address and (collect_time >= :startTime and collect_time <= :endTime) ", nativeQuery = true)
	int countRecByDevId(@Param("comm_address") String commAddress, 
			@Param("startTime") int startTime, @Param("endTime") int endTime);
	
	@Query(value = "select count(1) from zl_householder_data where "
			+ "collect_time >= :startTime and collect_time <= :endTime", nativeQuery = true)
	int countRecords(@Param("startTime") int startTime, @Param("endTime") int endTime);
}
