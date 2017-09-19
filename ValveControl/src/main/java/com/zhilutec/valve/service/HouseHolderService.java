/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年8月20日 上午10:45:13 * 
*/
package com.zhilutec.valve.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.bean.models.TblHouseHolder;
import com.zhilutec.valve.repository.HouseHolderRepo;
import com.zhilutec.valve.util.error.ErrorCode;
import com.zhilutec.valve.util.error.ErrorResponeMsgBody;
import com.zhilutec.valve.util.validator.RegexUtil;

@Service
public class HouseHolderService {
	@Autowired
	private HouseHolderRepo houseHolderRepo;
	
	// 设置上报周期
	@Transactional
	@Modifying
	public ErrorResponeMsgBody updatePeriodByDeveuis(String params) {
		JSONObject json = JSON.parseObject(params);
		Long period = json.getLong("period_setting");
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<String> euiList = new ArrayList<>();
		// 判断设备是否添加
		for (Object deveui : deveuis) {
			String eui = deveui.toString();
			int num = countByEui(eui);
			if (num == 0) {
				continue;
			}
			euiList.add(deveui.toString());
		}

		if (euiList.isEmpty()) {
			return new ErrorResponeMsgBody(ErrorCode.DEV_NOTFOUND);
		}

		// 批量更新设备信息
		int rs = houseHolderRepo.updatePeriodByEuis(euiList, period);
		if (rs == 0) {
			return new ErrorResponeMsgBody(ErrorCode.NODATA_ERR);
		}

		return new ErrorResponeMsgBody(ErrorCode.OK);
	}

	public int countByEui(String eui) {
		return houseHolderRepo.countHH(eui);
	}

	// 设置供热季
	@Transactional
	@Modifying
	public ErrorResponeMsgBody updateHotseasonByEuis(String params) {
		JSONObject json = JSON.parseObject(params);
		String hSeasonBegin = json.getString("heating_season_begin");
		String hSeasonEnd = json.getString("heating_season_end");
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<String> euiList = new ArrayList<>();
		// 判断设备是否添加
		for (Object deveui : deveuis) {
			String eui = deveui.toString();
			int num = countByEui(eui);
			if (num == 0) {
				continue;
			}
			euiList.add(deveui.toString());
		}

		if (euiList.isEmpty()) {
			return new ErrorResponeMsgBody(ErrorCode.DEV_NOTFOUND);
		}

		// 批量更新设备信息
		int rs = houseHolderRepo.updateHotseansons(euiList, hSeasonBegin, hSeasonEnd);
		if (rs == 0) {
			return new ErrorResponeMsgBody(ErrorCode.NODATA_ERR);
		}

		return new ErrorResponeMsgBody(ErrorCode.OK);
	}

	// 设置供热季和上报周期
	@Transactional
	@Modifying
	public ErrorResponeMsgBody updateSeasonsAndPeriods(String params) {
		JSONObject json = JSON.parseObject(params);
		// JSONObject hSeasonJson = json.getJSONObject("heating_season");
		Long period_setting = json.getLong("period_setting");
		String heating_season_begin = json.getString("heating_season_begin");
		String heating_season_end = json.getString("heating_season_end");
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		
		// 若设备记录不存在，则需要插入一条
		List<String> result = new ArrayList<String>();
		
		List<String> listBeans = JSON.parseArray(deveuis.toString(), String.class);
		for (String comm_address : listBeans) {
			if (comm_address.length() > 16) {
				continue;
			}
//			System.out.println("comm_address:" + comm_address);		
			TblHouseHolder obj = houseHolderRepo.findOne(comm_address);
			if (obj == null) {
				obj = new TblHouseHolder();
				obj.setComm_address(comm_address);
				obj.setComm_type(0); // lora
				obj.setCollect_time((long) (new Date().getTime()/1000));
				obj.setPeriod_setting(period_setting);
				obj.setHeating_season_begin(heating_season_begin);
				obj.setHeating_season_end(heating_season_end);
				houseHolderRepo.save(obj);
			}
			else
			{
				result.add(comm_address);
			}
		}
		
		if (result.isEmpty()) {
			return new ErrorResponeMsgBody(ErrorCode.DEV_NOTFOUND);
		}
		
		int rs = 0;
		if (period_setting == null && RegexUtil.isNotNull(heating_season_begin) && RegexUtil.isNotNull(heating_season_end)) {
			rs = houseHolderRepo.updateHotseansons(result, heating_season_begin, heating_season_end);
		} else if (period_setting != null && (RegexUtil.isNull(heating_season_begin) && RegexUtil.isNull(heating_season_end))) {
			rs = houseHolderRepo.updatePeriodByEuis(result, period_setting);
		} else if (period_setting != null && (RegexUtil.isNotNull(heating_season_begin) && RegexUtil.isNotNull(heating_season_end))) {
			rs = houseHolderRepo.updateSeasonsPeriods(result, period_setting, heating_season_begin, heating_season_end);
		}
		if (rs == 0) {
			return new ErrorResponeMsgBody(ErrorCode.NODATA_ERR);
		}

		return new ErrorResponeMsgBody(ErrorCode.OK);
	}

	// 3.1.7 Lora设备上报周期配置查询
	public List<TblHouseHolder> getHouseValvePeriod(String params) {
		JSONObject json = JSON.parseObject(params);
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<TblHouseHolder> periods = new ArrayList<>();
		List<TblHouseHolder> devs = houseHolderRepo.getValves(deveuis);
		for (int i = 0; i < devs.size(); i++) {
			TblHouseHolder period = new TblHouseHolder(true);
			TblHouseHolder dev = devs.get(i);
			period.setComm_address(dev.getComm_address());
			period.setPeriod_setting(dev.getPeriod_setting());
			periods.add(period);
		}
		return periods;
	}

	// 3.1.7 Lora设备供热季查询
	public List<TblHouseHolder> getHouseValveSeason(String params) {
		JSONObject json = JSON.parseObject(params);
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<TblHouseHolder> periods = new ArrayList<>();
		List<TblHouseHolder> devs = houseHolderRepo.getValves(deveuis);
		for (int i = 0; i < devs.size(); i++) {
			TblHouseHolder period = new TblHouseHolder(true);
			TblHouseHolder dev = devs.get(i);
			period.setComm_address(dev.getComm_address());
			period.setHeating_season_begin(dev.getHeating_season_begin());
			period.setHeating_season_end(dev.getHeating_season_end());
			periods.add(period);
		}
		return periods;
	}

	// 4.4.1 批量查询住户最新数据
	// 逐个查询
	public List<TblHouseHolder> getHouseVavlesByEuis(String params) {
		JSONObject json = JSON.parseObject(params);
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<TblHouseHolder> devList = new ArrayList<>();
		// 判断设备是否添加
		for (Object deveui : deveuis) {
			String eui = deveui.toString();
			int num = countByEui(eui);
			if (num == 0) {
				continue;
			}
			TblHouseHolder dev = houseHolderRepo.findHouseHolder(eui);
			devList.add(dev);
		}
		return devList;
	}

	// `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
	// `period_setting` int(11) DEFAULT '-1' COMMENT '上报周期',
	// `heating_season_begin` int(11) DEFAULT '0' COMMENT '供热季开始',
	// `heating_season_end` int(11) DEFAULT '0' COMMENT '供热季结束',
	// `ifpush` tinyint(4) DEFAULT '0' COMMENT '是否已经推送给第三方服务',
	// 4.4.1 批量查询住户最新数据
	public List<TblHouseHolder> getHouseValvesByDeveuis(String params) {
		JSONObject json = JSON.parseObject(params);
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<TblHouseHolder> devs = houseHolderRepo.getValves(deveuis);
		for (int i = 0; i < devs.size(); i++) {
			TblHouseHolder dev = devs.get(i);
			dev.setPeriod_setting(null);
			dev.setHeating_season_begin(null);
			dev.setHeating_season_end(null);	
		}
		return devs;
	}

	// 批量查询住户供热季上报周期
	public List<TblHouseHolder> getSeansonsAndPeriods(String params) {
		JSONObject json = JSON.parseObject(params);
		JSONArray deveuis = json.getJSONArray("comm_addresses");
		List<TblHouseHolder> results = new ArrayList<>();
		List<TblHouseHolder> devs = houseHolderRepo.getValves(deveuis);

		for (int i = 0; i < devs.size(); i++) {
			TblHouseHolder period = new TblHouseHolder(true);
			TblHouseHolder dev = devs.get(i);
			period.setComm_address(dev.getComm_address());
			period.setHeating_season_begin(dev.getHeating_season_begin());
			period.setHeating_season_end(dev.getHeating_season_end());
			period.setPeriod_setting(dev.getPeriod_setting());
			results.add(period);
		}
		return results;
	}

}
