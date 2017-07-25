package com.zhilu.device.service;

import static org.mockito.Matchers.booleanThat;

import java.sql.Date;
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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.zhilu.device.util.CheckParams;
import com.zhilu.device.util.PubMethod;
import com.zhilu.device.util.Result;
import com.zhilu.device.util.ResultDevAdd;
import com.zhilu.device.util.ResultErr;
import com.zhilu.device.util.ResultStatusCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilu.device.bean.TblIotDevice;
import com.zhilu.device.bean.TblIotDeviceBasic;
import com.zhilu.device.bean.TblIotDeviceDyn;
import com.zhilu.device.repository.TblIotDevBasicRepo;
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

	public TblIotDevice findById(String id) {
		TblIotDevice rs = tblIotDevRepo.findTblIotDeviceById(id);
		return rs;
	}

	public TblIotDevice findByUidMac(String userid, String mac) {
		return tblIotDevRepo.findTblIotDeviceByUseridAndMac(userid, mac);
	}

	// 创建设备组并添加
	// @return 返回添加成功和添加失败的,未存在的添加,存在的不添加,不会返回错误码
	public Map<String, List> addDevicesOld(String userid, String devname, String productid, Integer protocol,
			String[] macs) {
		List<TblIotDevice> devicesObj = new ArrayList<>();
		List<String> exsitedDevs = new ArrayList<>();
		for (String mac : macs) {
			TblIotDevice listDev = this.getDevByMac(mac);

			// 设备mac不存在则添加，存在则不添加
			if (listDev == null) {
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
	public Map<String, List> addDevices(JSONObject paramsJson) {
		// 通过json解析参数
		String userid = paramsJson.get("userid").toString();
		String devname = paramsJson.get("name").toString();
		String productid = paramsJson.get("product").toString();
		int protocol = Integer.parseInt(paramsJson.get("protocol").toString());
		// 得到Id数组
		String[] macsArray = PubMethod.getDevids(paramsJson);

		List<TblIotDevice> addDevs = new ArrayList<>();// 保存要添加的设备
		List<TblIotDeviceBasic> addDevsBasic = new ArrayList<>();// 保存要添加的设备
		List<TblIotDeviceDyn> addDevsDyn = new ArrayList<>();// 保存要添加的设备

		List<String> exsitedDevs = new ArrayList<>(); // 保存已存的设备

		for (String mac : macsArray) {
			// ******************************************************************
			// ******************此处容易出错,要去除引号***************************
			// ******************************************************************
			mac = PubMethod.removeQuto(mac);
			// ******************************************************************
			// ******************************************************************
			// ******************************************************************

			// 查询设备mac是否存在,不存在则添加，存在则不添加
			TblIotDevice listDev = this.getDevByMac(mac);
			// if ((listDev == null) || listDev.isEmpty()) {
			if ((listDev == null)) {
				TblIotDevice device = new TblIotDevice();
				TblIotDeviceBasic deviceBasic = new TblIotDeviceBasic();
				TblIotDeviceDyn deviceDyn = new TblIotDeviceDyn();
				String id = generateId();
				// device表数据
				device.setId(id);
				device.setUserid(userid);
				device.setName(devname);
				device.setProductid(productid);
				device.setProtocol(protocol);
				device.setMac(PubMethod.removeQuto(mac));
				device.setCreatetime(PubMethod.str2timestamp());
				addDevs.add(device);

				// 添加basic表数据
				deviceBasic.setDeviceid(id);
				deviceBasic.setUserid(userid);
				deviceBasic.setName(devname);
				deviceBasic.setCreatetime(PubMethod.str2timestamp());
				addDevsBasic.add(deviceBasic);

				// 添加dyn表数据
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

	private String generateId() {
		// 生成设备id,如果有重复id重新生成
		String id = PubMethod.generateDevId(ID_NUM); // 测试用
		Object idExisted = null;
		int i = 0;
		do {
			idExisted = this.getDevById(id);
			if (i == LOOP) {
				break;
			}
			id = PubMethod.generateDevId(ID_NUM);
			i++;
		} while ((idExisted == null));
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

		Sort sort = new Sort(orderCreateTime, orderId);
		pageNumber = pageNumber - 1; // 页码是从0开始的
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
		PageRequest pageReq = this.buildPageRequest(page, size);
		Page<TblIotDevice> devs = this.tblIotDevRepo.findAll(DevSpec.devSearchSpec(uid, type, search), pageReq);
		return devs;
	}

	public Page<TblIotDevice> findBySpec(String uid, Integer type, Integer page, Integer size) {
		PageRequest pageReq = this.buildPageRequest(page, size);
		new DevSpec();
		Page<TblIotDevice> devs = this.tblIotDevRepo.findAll(DevSpec.devSearchSpec(uid, type), pageReq);
		return devs;
	}

	// 更新设备信息
	@Transactional
	@Modifying
	public Result updateDev(JSONObject paramsJson) {
		Result rs = null;
		// 通过json解析参数
		String userid = paramsJson.get("userid").toString();
		String name = paramsJson.get("name").toString();
		String productId = paramsJson.get("product").toString();
		int protocol = Integer.parseInt(paramsJson.get("protocol").toString());
		// 得到Id数组
		String[] macsArray = PubMethod.getDevids(paramsJson);

		for (String mac : macsArray) {
			TblIotDevice dev = this.findDevbyUidAndMac(userid, mac);
			if (dev == null) {
				rs = new ResultErr(ResultStatusCode.DEVID_NOT_EXISTED.getCode(),
						ResultStatusCode.DEVID_NOT_EXISTED.getErrmsg());
				return rs;
			} else {
				String devId = dev.getId();
				TblIotDeviceBasic devBasic = tblIotDevBasicSrv.getById(devId);
				// 更新设备信息
				dev.setName(name);
				dev.setProtocol(protocol);
				dev.setProductid(productId);
				devBasic.setName(name);
				tblIotDevBasicSrv.saveDevicesBasic(devBasic);
				tblIotDevRepo.save(dev);
				rs = new ResultDevAdd(ResultStatusCode.OK.getCode(), macsArray);
				return rs;
			}
		}
		return rs;
	}

	// 此处mac可使用设备imei等设备id替代
	public TblIotDevice findDevbyUidAndMac(String userid, String mac) {
		mac = PubMethod.removeQuto(mac);
		return tblIotDevRepo.findTblIotDeviceByUseridAndMac(userid, mac);
	}

	// mac可使用imei等设备唯一标识替换
	@Transactional
	public String deleteByIds(JSONObject paramsJson) {
		String id = null;
		// 通过json解析参数
		String userid = paramsJson.get("userid").toString();
		String mac = paramsJson.get("devid").toString();

		TblIotDevice dev = findDevbyUidAndMac(userid, mac);
		if (dev != null) {
			id = dev.getId();
			this.tblIotDevRepo.deleteTblIotDeviceById(id);

			this.tblIotDevBasicSrv.deleteById(id);
			this.tblIotDevDynSrv.deleteById(id);
		}
		return id;
	}

	// 根据id查询单个设备
	public TblIotDevice getDevById(String id) {
		TblIotDevice dev = tblIotDevRepo.findTblIotDeviceById(id);
		return dev;
	}

	// 根据mac imei查询设备
	public TblIotDevice getDevByMac(String mac) {
		TblIotDevice dev = tblIotDevRepo.findTblIotDeviceByMac(mac);
		return dev;
	}

	// 获取设备id
	public static ArrayList<String> getDevId(TblIotDevice tblIotDevice) {
		String devId = tblIotDevice.getId();
		ArrayList<String> listDevIds = new ArrayList<>();
		listDevIds.add(devId);
		return listDevIds;
	}

	// 获取设备id
	public static ArrayList<String> getDevId(List<TblIotDevice> devs) {
		ArrayList<String> listDevIds = new ArrayList<>();
		java.util.Iterator<TblIotDevice> it = devs.iterator();
		while (it.hasNext()) {
			TblIotDevice dev = it.next();
			String devId = dev.getId();
			listDevIds.add(devId);
		}
		return listDevIds;
	}

	public List getDevsInfoById(String id) {
		List devInfo = tblIotDevRepo.getDevsAllInfoById(id);
		return devInfo;
	}

	public Map pageByUserid(String userid, Long page, Long pages) {
		Long start = (page - 1) * pages;// 数据库记录编号是从0开始,这里要减1
		List list = tblIotDevRepo.getDevsAllInfoByUserid(userid, start, pages);
		Map<String, Object> totalMap = parseDevAllInfo(list);
		return totalMap;
	}

	public Map pageByName(String userid, String search, Long page, Long pages) {
		Long start = (page - 1) * pages;
		List list = tblIotDevRepo.getDevsAllInfoByName(userid, search, start, pages);
		Map<String, Object> totalMap = parseDevAllInfo(list);
		return totalMap;
	}

	public Map<String, Object> pageInfo(int type, String userid, String search, Long page, Long pages) {
		Long start = (page - 1) * pages;
		List<?> list = null;
		if (type == 0) {
			// 所有
			list = tblIotDevRepo.getDevsAllInfoByUserid(userid, start, pages);
		} else if (type == 1) {
			// name
			list = tblIotDevRepo.getDevsAllInfoByName(userid, search, start, pages);
		} else if (type == 2) {
			// id
			list = tblIotDevRepo.getDevsAllInfoById(userid, search, start, pages);
		} else if (type == 3) {
			// product
			list = tblIotDevRepo.getDevsAllInfoByProduct(userid, search, start, pages);
		} else if (type == 4) {
			// groupid
			list = tblIotDevRepo.getDevsAllInfoByGroupid(userid, search, start, pages);
		} else if (type == 5) {
			// mac
			list = tblIotDevRepo.getDevsAllInfoByMac(userid, search, start, pages);
		} else {
		}
		Map<String, Object> totalMap = parseDevAllInfo(list);
		return totalMap;
	}

	// {
	// "errcode":0,
	// "totalRows":128,
	// "devices":[
	// {
	// "devId":"02:13:18:10:12:3a",
	// "name":"科兴设备01",
	// "product":"\u5c4f\u5e55\u663e\u793a",
	// "protocol":2,
	// "status":2,
	// "LoginTime":"2017-04-20 13:31:07",
	// "LogoutTime":"2017-04-20 13:31:07"
	// }
	// ]
	// }
	public Map getDevsAllInfo() {
		List devInfo = tblIotDevRepo.getDevsAllInfo();
		Map<String, Object> totalMap = parseDevAllInfo(devInfo);
		return totalMap;
	}

	/**
	 * @Title: parseDevAllInfo @Description: TODO @param @param
	 *         devInfo @param @return @return Map<String,Object> @throws
	 */
	private Map<String, Object> parseDevAllInfo(List devInfo) {
		Map<String, Object> totalMap = new HashMap<String, Object>();
		Long number = tblIotDevRepo.getDevTotalNum();
		totalMap.put("code", 0);
		totalMap.put("totalRows", number);
		ArrayList<Map> devs = new ArrayList<>();

		for (Object dev : devInfo) {
			Map<String, Object> devMap = new HashMap<String, Object>();
			Object[] cells = (Object[]) dev;

			for (int i = 0; i < cells.length; i++) {
				Object cell = cells[i];
				if (CheckParams.isNull(cell)) {
					cells[i] = "";
				}
			}

			devMap.put("id", cells[0]);
			devMap.put("devId", cells[1]);
			devMap.put("name", cells[2]);
			devMap.put("product", cells[3]);
			devMap.put("protocol", cells[4]);
			devMap.put("status", cells[5]);
			devMap.put("Logintime", cells[6]);
			devMap.put("Logintime", cells[7]);
			devs.add(devMap);
		}
		totalMap.put("devices", devs);
		return totalMap;
	}

	// 通过名称查询
	public List<?> getDevsInfoByName(String name) {
		List<TblIotDevice> devs = tblIotDevRepo.getDevsByName(name);
		for (TblIotDevice dev : devs) {
			String devid = dev.getId();
			tblIotDevRepo.getDevsAllInfoById(devid);
		}
		return devs;
	}

	public Result getDevStatusInfo(JSONObject paramsJson) {
		Result rs = null;
		String devmac = paramsJson.get("device_id").toString();
		Long time = Long.parseLong(paramsJson.get("time").toString());
		Integer onlineStatus = Integer.parseInt(paramsJson.get("onlineStatus").toString());
		TblIotDevice dev = tblIotDevRepo.findTblIotDeviceByMac(devmac);
		String id = dev.getId();
		TblIotDeviceBasic devBasic = tblIotDevBasicSrv.findById(id);
		int status = devBasic.getStatus();
		TblIotDeviceDyn devDyn = tblIotDevDynSrv.findById(id);
		Long onlineTime = devDyn.getOnlinetime();

		if ((status == onlineStatus) && (onlineTime == time)) {
			rs = new ResultDevAdd(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getErrmsg());
			return rs;
		} else if ((status != onlineStatus)) {
			rs = new ResultDevAdd(ResultStatusCode.ONLINE_STATUS_ERR.getCode(),
					ResultStatusCode.ONLINE_STATUS_ERR.getErrmsg());
			return rs;
		} else if (onlineTime != time) {
			rs = new ResultDevAdd(ResultStatusCode.ONLINE_TIME_ERR.getCode(),
					ResultStatusCode.ONLINE_TIME_ERR.getErrmsg());
			return rs;
		} else {
			rs = new ResultDevAdd(ResultStatusCode.UNKNOWN_ERR.getCode(), ResultStatusCode.UNKNOWN_ERR.getErrmsg());
			return rs;
		}
	}
}
