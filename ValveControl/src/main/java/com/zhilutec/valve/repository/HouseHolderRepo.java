/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月17日 上午11:05:00 * 
*/
package com.zhilutec.valve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alibaba.fastjson.JSONArray;
import com.zhilutec.valve.bean.TblHouseHolder;

public interface HouseHolderRepo
		extends JpaRepository<TblHouseHolder, String>, JpaSpecificationExecutor<TblHouseHolder> {

	// 更新单个设备上报周期
	@Modifying
	@Query("update TblHouseHolder hh set hh.period_setting = :period_setting where hh.comm_address = :comm_address")
	int updatePeriodByEui(@Param(value = "comm_address") String comm_address,
			@Param(value = "period_setting") Long period_setting);

	// 更新一组设备上报周期
	@Modifying
	@Query("update TblHouseHolder hh set hh.period_setting = :period_setting where hh.comm_address in :comm_addresses")
	int updatePeriodByEuis(@Param(value = "comm_addresses") JSONArray comm_addresses,
			@Param(value = "period_setting") Long period_setting);

	// 更新单个设备供热季
	@Modifying
	@Query("update TblHouseHolder hh "
			+ "set hh.heating_season_begin = :heating_season_begin,hh.heating_season_end = :heating_season_end "
			+ "where hh.comm_address = :comm_address")
	int updateHotseanson(@Param(value = "comm_address") String comm_addresse,
			@Param(value = "heating_season_begin") String heating_season_begin,
			@Param(value = "heating_season_end") String heating_season_end);

	// 更新一组设备供热季
	@Modifying
	@Query("update TblHouseHolder hh "
			+ "set hh.heating_season_begin = :heating_season_begin,hh.heating_season_end = :heating_season_end "
			+ "where hh.comm_address in :comm_addresses")
	int updateHotseansons(@Param(value = "comm_addresses") JSONArray comm_addresses,
			@Param(value = "heating_season_begin") String heating_season_begin,
			@Param(value = "heating_season_end") String heating_season_end);

	//更新供热季和周期
	@Modifying
	@Query("update TblHouseHolder hh "
			+ "set hh.heating_season_begin = :heating_season_begin,hh.heating_season_end = :heating_season_end, "
			+ "hh.period_setting = :period_setting " + "where hh.comm_address in :comm_addresses")
	int updateSeasonsPeriods(@Param(value = "comm_addresses") JSONArray comm_addresses,
			@Param(value = "period_setting") Long period_setting,
			@Param(value = "heating_season_begin") String heating_season_begin,
			@Param(value = "heating_season_end") String heating_season_end);

	@Query("select count(hh) from TblHouseHolder hh where hh.comm_address = :comm_address")
    Integer countHH(@Param("comm_address") String comm_address);

	@Query("select hh from TblHouseHolder hh where hh.comm_address = :comm_address")
	TblHouseHolder findHouseHolder(@Param(value = "comm_address") String comm_address);

	@Query("select hh from TblHouseHolder hh where hh.comm_address in :comm_addresses")
	List<TblHouseHolder> getValves(@Param(value = "comm_addresses") JSONArray comm_addresses);
}
