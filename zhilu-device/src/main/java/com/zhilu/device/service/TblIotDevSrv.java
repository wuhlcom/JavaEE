package com.zhilu.device.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.zhilu.device.util.PubMethod;
import com.zhilu.device.bean.TblIotDevice;
import com.zhilu.device.bean.TblIotDeviceBasic;
import com.zhilu.device.bean.TblIotDeviceDyn;
import com.zhilu.device.repository.TblIotDevRepo;

@Service
public class TblIotDevSrv {

	public final static String ADDED = "add";
	public final static String EXISITED = "existed";
	public final static int LOOP = 100;
	public final static int ID_NUM = 13;
	
	@Autowired
	private TblIotDevRepo tblIotDevRepo;	

	@Autowired
	private TblIotDevBasicSrv tblIotDevBasicSrv;
	
	@Autowired
	private TblIotDevDynSrv tblIotDevDynSrv;

	// 创建设备组并添加
	//
	// @return 返回添加成功和添加失败的,未存在的添加,存在的不添加
	public Map<String, List> addDevicesOld(String userid, String devname, String productid, Integer protocol,
			String[] macs) {
		List<TblIotDevice> devicesObj = new ArrayList<>();
		List<String> exsitedDevs = new ArrayList<>();
		for (String mac : macs) {
			List<TblIotDevice> listDev = this.getDevByMac(mac);

			// 设备mac不存在则添加，存在则不添加
			if ((listDev == null) || listDev.isEmpty()) {
				TblIotDevice device = new TblIotDevice();
				String strId = PubMethod.generateDevId(ID_NUM); // 测试用
				device.setId(strId);
				device.setUserid(userid);
				device.setName(devname);
				device.setProductid(productid);
				device.setProtocol(protocol);
				device.setMac(PubMethod.removeQuto(mac));
				device.setCreatetime(PubMethod.str2timestamp());
				devicesObj.add(device);
			} else {
				exsitedDevs.add(mac);
			}
		}
		ArrayList<?> devMacs = saveDevices(devicesObj);
		Map<String, List> devMacMap = new HashMap<String, List>();
		devMacMap.put(ADDED, devMacs);
		devMacMap.put(EXISITED, exsitedDevs);
		return devMacMap;
	}

	// 创建设备组并添加
	// @return 返回添加成功和添加失败的,只有重复的所有的设备都不添加
	public Map<String, List> addDevices(String userid, String devname, String productid, Integer protocol,
			String[] macs) {

		List<TblIotDevice> addDevs = new ArrayList<>();// 保存要添加的设备
		List<TblIotDeviceBasic> addDevsBasic = new ArrayList<>();// 保存要添加的设备
		List<TblIotDeviceDyn> addDevsDyn = new ArrayList<>();// 保存要添加的设备
		
		List<String> exsitedDevs = new ArrayList<>(); // 保存已存的设备

		for (String mac : macs) {
			// ******************************************************************
			// ******************此处容易出错,要去除引号***************************
			// ******************************************************************
			mac = PubMethod.removeQuto(mac);
			// ******************************************************************
			// ******************************************************************
			// ******************************************************************

			// 查询设备mac是否存在,不存在则添加，存在则不添加
			List<TblIotDevice> listDev = this.getDevByMac(mac);
			if ((listDev == null) || listDev.isEmpty()) {
				TblIotDevice device = new TblIotDevice();
				TblIotDeviceBasic deviceBasic = new TblIotDeviceBasic();
				TblIotDeviceDyn deviceDyn = new TblIotDeviceDyn();
				String id = generateId();
				//device表数据
				device.setId(id);
				device.setUserid(userid);
				device.setName(devname);
				device.setProductid(productid);
				device.setProtocol(protocol);
				device.setMac(PubMethod.removeQuto(mac));
				device.setCreatetime(PubMethod.str2timestamp());
				addDevs.add(device);
				
				//添加basic表数据
				deviceBasic.setDeviceid(id);
				deviceBasic.setUserid(userid);
				deviceBasic.setName(devname);				
				deviceBasic.setCreatetime(PubMethod.str2timestamp());
				addDevsBasic.add(deviceBasic);				
				
				//添加dyn表数据
				deviceDyn.setDeviceid(id);
				addDevsDyn.add(deviceDyn);
						
			} else {
				exsitedDevs.add(mac);
			}
		}
		Map<String, List> devMacMap = new HashMap<String, List>();
		
		// 只要有已存在在设备就不添加任何设备
		for (String string : exsitedDevs) {
			System.out.println(string);
		}
		
//		System.out.println("--------------------"+this.getClass()+"-----------------------");
//		System.out.println(tblIotDevBasicSrv);
//		tblIotDevBasicSrv.saveDevicesBasic(addDevsBasic);
//		System.out.println("--------------------"+this.getClass()+"-----------------------");
//		return null;
		tblIotDevDynSrv.saveDevicesDyn(addDevsDyn);
		if ((exsitedDevs == null) || exsitedDevs.isEmpty()) {
			ArrayList<String> devMacs = saveDevices(addDevs);			
			devMacMap.put(ADDED, devMacs);
		tblIotDevBasicSrv.saveDevicesBasic(addDevsBasic);
		tblIotDevDynSrv.saveDevicesDyn(addDevsDyn);
		} else {
			devMacMap.put(EXISITED, exsitedDevs);
		}
		return devMacMap;
	}

	/**  
	* @Title: generateId  
	* @Description: TODO 
	* @param @return   
	* @return String   
	* @throws  
	*/
	private String generateId() {
		// 生成设备id,如果有重复id重新生成
		String id = PubMethod.generateDevId(ID_NUM); // 测试用
		List<?> idExisted = null;
		int i = 0;
		do {
			idExisted = this.getDevById(id);
			if (i == LOOP) {
				break;
			}
			id = PubMethod.generateDevId(ID_NUM);
			i++;
		} while ((idExisted == null) || idExisted.isEmpty());
		return id;
	}

	// 添加设备组
	// 返回设备ID数组
	@Transactional
	public ArrayList<String> saveDevices(List<TblIotDevice> devices) {
		List<TblIotDevice> rsSave = tblIotDevRepo.save(devices);
		// ArrayList<String> devIds = new ArrayList<>();
		ArrayList<String> devMacs = new ArrayList<>();
		for (TblIotDevice device : rsSave) {
			// devids.add(device.getId());//得所有id
			devMacs.add(device.getMac());
		}
		return devMacs;
	}	
	
	/**
	 * 分页查询
	 */
	public Page<TblIotDevice> getDevsByPage(int pageNumber, int pageSize) {
		PageRequest request = this.buildPageRequest(pageNumber, pageSize);
		Page<TblIotDevice> tblIotDevices = this.tblIotDevRepo.findAll(request);
		return tblIotDevices;
	}

	/**
	 * 建立分页排序请求
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		// 指定排序方式
		Order orderId = new Order(Direction.DESC, "id");
		Order orderType = new Order(Direction.ASC, "type");
		Order orderCreateTime = new Order(Direction.DESC, "createtime");

		Sort sort = new Sort(orderCreateTime, orderType);
		pageNumber = pageNumber - 1;
		PageRequest pageable = new PageRequest(pageNumber, pagzSize, sort);
		return pageable;
	}

	/**
	 * 复杂查询测试
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<TblIotDevice> findBySpec(String uid, Integer type, String search, Integer page, Integer size) {
		if (search == null || search.length() < 0) {
			search = "0";
		}
		PageRequest pageReq = this.buildPageRequest(page, size);
		new DevSpec();
		Page<TblIotDevice> devs = this.tblIotDevRepo.findAll(DevSpec.devSearchSpec(uid, type, search), pageReq);
		return devs;
	}

	public Page<TblIotDevice> findBySpec(String uid, Integer type, Integer page, Integer size) {
		PageRequest pageReq = this.buildPageRequest(page, size);
		new DevSpec();
		Page<TblIotDevice> devs = this.tblIotDevRepo.findAll(DevSpec.devSearchSpec(uid, type), pageReq);
		return devs;
	}

	@Transactional
	@Modifying
	public TblIotDevice updateDev(String id, String... args) {
		TblIotDevice tblIotDevObj = new TblIotDevice();
		tblIotDevObj.setId(id);
		tblIotDevObj.setName(args[0]);
		return tblIotDevRepo.save(tblIotDevObj);
	}

	@Transactional
	public void deleteById(String id, String userid) {
		tblIotDevRepo.deleteByUseridAndId(id, userid);
	}

	// 根据id查询单个设备
	public List<TblIotDevice> getDevById(String id) {
		List<TblIotDevice> dev = tblIotDevRepo.findTblIotDeviceById(id);
		return dev;
	}

	// 根据mac imei查询设备
	public List<TblIotDevice> getDevByMac(String mac) {
		List<TblIotDevice> dev = tblIotDevRepo.findTblIotDeviceByMac(mac);
		return dev;
	}

	private class MySpec implements Specification<TblIotDevice> {
		@Override
		public Predicate toPredicate(Root<TblIotDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

			// 1.混合条件查询
			/*
			 * Path<String> exp1 = root.get("taskName"); Path<Date> exp2 =
			 * root.get("createTime"); Path<String> exp3 =
			 * root.get("taskDetail"); Predicate predicate =
			 * cb.and(cb.like(exp1, "%taskName%"),cb.lessThan(exp2, new
			 * Date())); return cb.or(predicate,cb.equal(exp3, "kkk"));
			 * 
			 * 类似的sql语句为: Hibernate: select count(task0_.id) as col_0_0_ from
			 * tb_task task0_ where ( task0_.task_name like ? ) and
			 * task0_.create_time<? or task0_.task_detail=?
			 */

			// 2.多表查询
			/*
			 * Join<Task,Project> join = root.join("project", JoinType.INNER);
			 * Path<String> exp4 = join.get("projectName"); return cb.like(exp4,
			 * "%projectName%");
			 * 
			 * Hibernate: select count(task0_.id) as col_0_0_ from tb_task
			 * task0_ inner join tb_project project1_ on
			 * task0_.project_id=project1_.id where project1_.project_name like
			 * ?
			 */
			return null;
		}
	}

}
