package com.zhilutec.valve.service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhilutec.valve.bean.TblHouseHolderData;
import com.zhilutec.valve.repository.HouseHolderDataRepo;
import com.zhilutec.valve.util.error.ErrorCode;

import com.zhilutec.valve.util.error.GlobalErrorException;
import com.zhilutec.valve.util.validator.StealingCondition;

@Service
public class HouseHolderDataService {

	public final static Logger logger = LoggerFactory.getLogger(HouseHolderDataService.class);

	@Autowired
	private HouseHolderDataRepo houseHolderDataRepo;

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
			dataMap.put("house_code", cells[0]);
			dataMap.put("comm_address", cells[1]);
			dataMap.put("collect_time", cells[2]);
			dataMap.put("supply_temp", cells[3]);
			dataMap.put("return_temp", cells[4]);
			dataMap.put("valve_state", cells[5]);
			datas.add(dataMap);
		}
		return datas;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TblHouseHolderData> getStealing(StealingCondition condition) throws GlobalErrorException {
		List rs = new ArrayList();
		List<Object[]> stealingList = new ArrayList<>();
		try {
			String con1 = condition.getCondition1();
			if (con1 != null) {
				con1 = con1.toLowerCase();
			}else{
				con1="and";
			}
			String con2 = condition.getCondition2();
			if (con2 != null) {
				con2 = con2.toLowerCase();
			}else{
				con2="and";
			}
			if (con1.equals("or") && con2.equals("and")) {
				stealingList = houseHolderDataRepo.findStealingOrAnd(condition.getStart_time(), condition.getEnd_time(),
						condition.getWit_min(), condition.getWit_max(), condition.getWot_min(), condition.getWot_max(),
						condition.getTemdif());
			} else if (con1.equals("and") && con2.equals("or")) {
				stealingList = houseHolderDataRepo.findStealingAndOr(condition.getStart_time(), condition.getEnd_time(),
						condition.getWit_min(), condition.getWit_max(), condition.getWot_min(), condition.getWot_max(),
						condition.getTemdif());
			} else if (con1.equals("or") && con2.equals("or")) {
				stealingList = houseHolderDataRepo.findStealing2Or(condition.getStart_time(), condition.getEnd_time(),
						condition.getWit_min(), condition.getWit_max(), condition.getWot_min(), condition.getWot_max(),
						condition.getTemdif());
			} else {
				stealingList = houseHolderDataRepo.findStealing(condition.getStart_time(), condition.getEnd_time(),
						condition.getWit_min(), condition.getWit_max(), condition.getWot_min(), condition.getWot_max(),
						condition.getTemdif());
			}
			rs = parseObjectArr(stealingList);
		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		return rs;
	}

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

	public List<TblHouseHolderData> getStealingGroup(StealingCondition condition) throws GlobalErrorException {
		List rs = new ArrayList();
		List<Object[]> stealingList = new ArrayList<>();
		try {
			String con1 = condition.getCondition1();
			String con2 = condition.getCondition2();
			if (con1.equals("or") && con2.equals("and")) {
				stealingList = houseHolderDataRepo.findStealingGroupOrAnd(condition.getStart_time(),
						condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(), condition.getWot_min(),
						condition.getWot_max(), condition.getTemdif());
			} else if (con1.equals("and") && con2.equals("or")) {
				stealingList = houseHolderDataRepo.findStealingGroupAndOr(condition.getStart_time(),
						condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(), condition.getWot_min(),
						condition.getWot_max(), condition.getTemdif());
			} else if (con1.equals("or") && con2.equals("or")) {
				stealingList = houseHolderDataRepo.findStealingGroup2Or(condition.getStart_time(),
						condition.getEnd_time(), condition.getWit_min(), condition.getWit_max(), condition.getWot_min(),
						condition.getWot_max(), condition.getTemdif());
			} else {
				stealingList = houseHolderDataRepo.findStealingGroup(condition.getStart_time(), condition.getEnd_time(),
						condition.getWit_min(), condition.getWit_max(), condition.getWot_min(), condition.getWot_max(),
						condition.getTemdif());
			}
			rs = parseObjectGroupArr(stealingList);
		} catch (Exception e) {
			logger.error("failed to get records by condition", e);
			throw new GlobalErrorException(ErrorCode.DB_ERROR);
		}
		return rs;
	}

}
