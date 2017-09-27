package com.zhilutec.valve.service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilutec.valve.bean.models.TblHouseHolderData;
import com.zhilutec.valve.bean.params.HouseHolderDataParamSpec;
import com.zhilutec.valve.bean.params.HouseHolderDataParams;
import com.zhilutec.valve.repository.HouseHolderDataRepo;
import com.zhilutec.valve.util.error.ErrorCode;

import com.zhilutec.valve.util.error.GlobalErrorException;
import com.zhilutec.valve.util.validator.RegexUtil;
import com.zhilutec.valve.util.validator.StealingCondition;
import com.zhilutec.valve.util.validator.StealingValidator;

@Service
public class HouseHolderDataService {

	public final static Logger logger = LoggerFactory.getLogger(HouseHolderDataService.class);

	@Autowired
	private HouseHolderDataRepo houseHolderDataRepo;

	// 解析返回结果中Object[]对象
	private List parseObjectArr(List<Object[]> objects) {
		ArrayList<Map> datas = new ArrayList<>();
		for (Object data : objects) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Object[] cells = (Object[]) data;
			for (int i = 0; i < cells.length; i++) {
				Object cell = cells[i];
				if (cell == null) {
					cells[i] = "";
				}
			}
			dataMap.put("comm_address", cells[0]);
			dataMap.put("collect_time", cells[1]);
			dataMap.put("supply_temp", cells[2]);
			dataMap.put("return_temp", cells[3]);
			dataMap.put("valve_state", cells[4]);
			datas.add(dataMap);
		}
		return datas;
	}

	// 盗热数据查询,旧接口
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TblHouseHolderData> getStealingOld(StealingCondition condition) throws GlobalErrorException {
		List rs = new ArrayList();
		List<Object[]> stealingList = new ArrayList<>();
		try {

			if ((condition.getTemdif() == null || condition.getTemdif() == 0) && (condition.getWit_min() == null)
					&& (condition.getWit_max() == null) && (condition.getWot_min() == null)
					&& (condition.getWot_max() == null)) {
				stealingList = houseHolderDataRepo.findStealingByTime(condition.getStart_time(),
						condition.getEnd_time());
			} else {
				String con1 = condition.getCondition1();
				if (con1 != null) {
					con1 = con1.toLowerCase();
				} else {
					con1 = "and";
				}
				String con2 = condition.getCondition2();
				if (con2 != null) {
					con2 = con2.toLowerCase();
				} else {
					con2 = "and";
				}
				if (con1.equals("or") && con2.equals("and")) {
					stealingList = houseHolderDataRepo.findStealingOrAnd(condition.getStart_time(),
							condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(),
							condition.getWot_min(), condition.getWot_max(), condition.getTemdif());
				} else if (con1.equals("and") && con2.equals("or")) {
					stealingList = houseHolderDataRepo.findStealingAndOr(condition.getStart_time(),
							condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(),
							condition.getWot_min(), condition.getWot_max(), condition.getTemdif());
				} else if (con1.equals("or") && con2.equals("or")) {
					stealingList = houseHolderDataRepo.findStealing2Or(condition.getStart_time(),
							condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(),
							condition.getWot_min(), condition.getWot_max(), condition.getTemdif());
				} else {
					stealingList = houseHolderDataRepo.findStealing(condition.getStart_time(), condition.getEnd_time(),
							condition.getWit_min(), condition.getWit_max(), condition.getWot_min(),
							condition.getWot_max(), condition.getTemdif());
				}
			}
			rs = parseObjectArr(stealingList);
		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		return rs;
	}

	// "collect_time": 1505437847,
	// "comm_address": "4776e6ed0021002c",
	// "comm_status": 0,
	// "comm_type": 0,
	// "opening": 100,
	// "return_temp": 28.7,
	// "supply_temp": 28.4,
	// "valve_state": 1

	private List<TblHouseHolderData> parseStealingRs(List<TblHouseHolderData> rs) {
		for (TblHouseHolderData houseHolderData : rs) {
			houseHolderData.setId(null);
			houseHolderData.setAccumulated_heat(null);
			houseHolderData.setAverage_temper(null);
			houseHolderData.setComm_status(null);
			houseHolderData.setComm_type(null);
			houseHolderData.setDtu_code(null);
			houseHolderData.setOpen_ratio(null);		
			houseHolderData.setSet_temper(null);
			houseHolderData.setOpen_time(null);
			houseHolderData.setSharing_heat(null);
			houseHolderData.setValve_state(null);
		}
		return rs;
	}

	// 盗热数据查询
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TblHouseHolderData> getStealing(String requestBody) throws GlobalErrorException {
		HouseHolderDataParams params = new HouseHolderDataParams();
		 JSONObject paramJson = JSON.parseObject(requestBody);
		 if (!StealingValidator.queryStealValidator(paramJson)) {
		 return null;
		 }
		params = JSON.parseObject(requestBody, HouseHolderDataParams.class);
		List rs = new ArrayList();
		Specification<TblHouseHolderData> conditions = HouseHolderDataParamSpec.conditions(params, 0);
		try {
			rs = houseHolderDataRepo.findAll(conditions);
			if (rs.size() != 0) {
				rs = parseStealingRs(rs);
			}
		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		return rs;
	}

	//
	private List parseObjectGroupArr(List<Object[]> objects) {
		ArrayList<Map> datas = new ArrayList<>();
		for (Object data : objects) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Object[] cells = (Object[]) data;
			for (int i = 0; i < cells.length; i++) {
				Object cell = cells[i];
				if (cell == null) {
					cells[i] = "";
				}
			}
			dataMap.put("comm_type", cells[0]);
			dataMap.put("comm_address", cells[1]);
			datas.add(dataMap);
		}
		return datas;
	}

	// 盗热住户查询
	public List<TblHouseHolderData> getStealingGroupOld(StealingCondition condition) throws GlobalErrorException {
		List rs = new ArrayList();
		List<Object[]> stealingList = new ArrayList<>();
		try {
			if ((condition.getTemdif() == null || condition.getTemdif() == 0) && (condition.getWit_min() == null)
					&& (condition.getWit_max() == null) && (condition.getWot_min() == null)
					&& (condition.getWot_max() == null)) {
				stealingList = houseHolderDataRepo.findStealingByTime(condition.getStart_time(),
						condition.getEnd_time());
			} else {
				String con1 = condition.getCondition1();
				String con2 = condition.getCondition2();

				if (con1 != null) {
					con1 = con1.toLowerCase();
				} else {
					con1 = "and";
				}

				if (con2 != null) {
					con2 = con2.toLowerCase();
				} else {
					con2 = "and";
				}

				if (con1.equals("or") && con2.equals("and")) {
					stealingList = houseHolderDataRepo.findStealingGroupOrAnd(condition.getStart_time(),
							condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(),
							condition.getWot_min(), condition.getWot_max(), condition.getTemdif());
				} else if (con1.equals("and") && con2.equals("or")) {
					stealingList = houseHolderDataRepo.findStealingGroupAndOr(condition.getStart_time(),
							condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(),
							condition.getWot_min(), condition.getWot_max(), condition.getTemdif());
				} else if (con1.equals("or") && con2.equals("or")) {
					stealingList = houseHolderDataRepo.findStealingGroup2Or(condition.getStart_time(),
							condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(),
							condition.getWot_min(), condition.getWot_max(), condition.getTemdif());
				} else {
					stealingList = houseHolderDataRepo.findStealingGroup(condition.getStart_time(),
							condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(),
							condition.getWot_min(), condition.getWot_max(), condition.getTemdif());
				}
			}
			rs = parseObjectGroupArr(stealingList);
		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		return rs;
	}

	private List<TblHouseHolderData> parseStealingGroupRs(List<TblHouseHolderData> rs) {
		for (TblHouseHolderData houseHolderData : rs) {
			houseHolderData.setId(null);
			houseHolderData.setAccumulated_heat(null);
			houseHolderData.setAverage_temper(null);
			houseHolderData.setComm_status(null);
			houseHolderData.setDtu_code(null);
			houseHolderData.setOpen_ratio(null);
			houseHolderData.setSet_temper(null);
			houseHolderData.setReturn_temp(null);
			houseHolderData.setSharing_heat(null);
			houseHolderData.setSupply_temp(null);
			houseHolderData.setOpen_time(null);
			houseHolderData.setCollect_time(null);
			houseHolderData.setValve_state(null);
		}
		return rs;
	}

	// 盗热住户查询
	public List<TblHouseHolderData> getStealingGroup(String requestBody) throws GlobalErrorException {
		HouseHolderDataParams params = new HouseHolderDataParams();
		params = JSON.parseObject(requestBody, HouseHolderDataParams.class);
		JSONObject paramJson = JSON.parseObject(requestBody);
		if (!StealingValidator.queryStealValidator(paramJson)) {
			return null;
		}
		List rs = new ArrayList();
		Specification<TblHouseHolderData> conditions = HouseHolderDataParamSpec.conditions(params, 1);
		try {
			rs = houseHolderDataRepo.findAll(conditions);
			if (rs.size() != 0) {
				rs = this.parseStealingGroupRs(rs);
			}
		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		return rs;
	}

}
