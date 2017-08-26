package com.zhilu.device.service.primary;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.stereotype.Service;

import com.zhilu.device.util.PubMethod;
import com.zhilu.device.util.errorcode.Result;
import com.zhilu.device.util.errorcode.ResultDevAdd;
import com.zhilu.device.util.errorcode.ResultErr;
import com.zhilu.device.util.errorcode.ResultStatusCode;
import com.zhilu.device.util.validator.CheckParams;
import com.zhilu.device.util.validator.FieldLimit;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhilu.device.bean.primary.TblIotDevice;
import com.zhilu.device.bean.primary.TblIotDeviceBasic;
import com.zhilu.device.bean.primary.TblIotDeviceDyn;
import com.zhilu.device.bean.secondary.LoraDevice;
import com.zhilu.device.bean.secondary.LoraGateway;
import com.zhilu.device.repository.primary.TblIotDevRepo;
import com.zhilu.device.service.DevSpec;
import com.zhilu.device.service.secondary.LoraDevSrv;
import com.zhilu.device.service.secondary.LoraGwSrv;

@Service
public class TblIotDevSrv {
	public final static int LOOP = 100;
	public final static int DEV_ID_LEN = 13;

	@Autowired
	private TblIotDevRepo tblIotDevRepo;

	@Autowired
	private TblIotDevBasicSrv tblIotDevBasicSrv;

	@Autowired
	private TblIotDevDynSrv tblIotDevDynSrv;

	@Autowired
	private LoraGwSrv loraGwSrv;

	@Autowired
	private LoraDevSrv loraDevSrv;

	public TblIotDevice findById(String id) {
		TblIotDevice rs = tblIotDevRepo.findTblIotDeviceById(id);
		return rs;
	}

	public TblIotDevice findByUidMac(String userid, String mac) {
		return tblIotDevRepo.findTblIotDeviceByUseridAndMac(userid, mac);
	}

	// 创建设备组并添加
	// @return 返回添加成功和添加失败的,只有重复的所有的设备都不添加
	@Transactional
	public Map<String, List> addDevices(JSONObject paramsJson) {
		// 通过json解析参数
		String userid = paramsJson.getString("userid");
		String name = paramsJson.getString("name");
		Integer type = paramsJson.getInteger("type");
		JSONObject type_params = paramsJson.getJSONObject("type_params");

		// 得到Id数组
		String[] macsArray = PubMethod.getDevids(paramsJson);

		List<TblIotDevice> addDevs = new ArrayList<>();// 保存要添加的设备
		List<TblIotDeviceBasic> addDevsBasic = new ArrayList<>();// 保存要添加的设备
		List<TblIotDeviceDyn> addDevsDyn = new ArrayList<>();// 保存要添加的设备
		List<LoraGateway> addLrGws = new ArrayList<>();// 保存要添加的设备
		List<LoraDevice> addLrDevs = new ArrayList<>();// 保存要添加的设备

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
			int exist = 0;

			// 判断网关在lora表中是否存在
			if (type == 3) {
				exist = loraGwSrv.queryLrGw(mac);
			}

			// 判断Lora设备在lora表中是否存在
			if (type == 4) {
				exist = loraDevSrv.queryLrDev(mac);
			}

			// 如果设备在mia和lora中都没有记录则添加
			if ((listDev == null) && (exist == 0)) {
				TblIotDevice device = new TblIotDevice();
				TblIotDeviceBasic deviceBasic = new TblIotDeviceBasic();
				TblIotDeviceDyn deviceDyn = new TblIotDeviceDyn();

				device = JSON.parseObject(paramsJson.toJSONString(), TblIotDevice.class);
				String id = generateId();
				Timestamp strTimestamp = PubMethod.str2timestamp();
				// device表数据
				device.setId(id);
				device.setMac(mac);
				device.setCreatetime(strTimestamp);
				addDevs.add(device);

				// 添加basic表数据
				deviceBasic = JSON.parseObject(paramsJson.toJSONString(), TblIotDeviceBasic.class);
				deviceBasic.setDeviceid(id);
				deviceBasic.setCreatetime(PubMethod.str2timestamp());
				addDevsBasic.add(deviceBasic);

				// 添加dyn表数据
				deviceDyn = JSON.parseObject(paramsJson.toJSONString(), TblIotDeviceDyn.class);
				deviceDyn.setDeviceid(id);
				addDevsDyn.add(deviceDyn);

				//添加lora gw
				if (type == 3) {
					addLrGw(userid, name, type_params, addLrGws, mac, strTimestamp);
				}

				//添加lora dev
				if (type == 4) {
					addLrDev(name, type_params, addLrDevs, mac, strTimestamp);
				}
			} else {
				exsitedDevs.add(mac);
			}
		}

		Map<String, List> devMacMap = new HashMap<String, List>();
		// 只要有已存在在设备就不添加任何设备
		for (String exsitedDev : exsitedDevs) {
			System.out.println("设备已经存在:" + exsitedDev);
		}

		// tblIotDevDynSrv.saveDevicesDyn(addDevsDyn);
		if ((exsitedDevs == null) || exsitedDevs.isEmpty()) {
			ArrayList<String> devMacs = saveDevices(addDevs);
			devMacMap.put("add", devMacs);
			tblIotDevBasicSrv.saveDevicesBasic(addDevsBasic);
			tblIotDevDynSrv.saveDevicesDyn(addDevsDyn);

			if (type == 3) {
				loraGwSrv.saveLrGw(addLrGws);
			}

			if (type == 4) {
				loraDevSrv.saveLrDev(addLrDevs);
			}

		} else {
			devMacMap.put("existed", exsitedDevs);
		}
		return devMacMap;
	}

	private void addLrDev(String name, JSONObject type_params, List<LoraDevice> addLrDevs, String mac,
			Timestamp strTimestamp) {
		LoraDevice lora = new LoraDevice();
		type_params.put("deveui", mac);
		type_params.put("name", name);
		lora = JSON.parseObject(type_params.toJSONString(), LoraDevice.class);
		lora.setCreatetime(strTimestamp);
		String uuid = this.generateLrGwUuid();
		lora.setUuid(uuid);
		addLrDevs.add(lora);
	}

	private void addLrGw(String userid, String name, JSONObject type_params, List<LoraGateway> addLrGws, String mac,
			Timestamp strTimestamp) {
		LoraGateway lora = new LoraGateway();
		type_params.put("userid", userid);
		type_params.put("mac", mac);
		type_params.put("name", name);

		lora = JSON.parseObject(type_params.toJSONString(), LoraGateway.class);
		lora.setCreatetime(strTimestamp);
		String uuid = this.generateLrGwUuid();
		lora.setUuid(uuid);	
		addLrGws.add(lora);
	}

	private String generateId() {
		// 生成设备id,如果有重复id重新生成
		String id = PubMethod.generateDevId(DEV_ID_LEN); // 测试用
		TblIotDevice idExisted = null;
		int i = 0;
		do {
			idExisted = this.getDevById(id);
			if (i == LOOP) {
				break;
			}
			id = PubMethod.generateDevId(DEV_ID_LEN);
			i++;
		} while (idExisted == null);
		return id;
	}

	private String generateLrGwUuid() {
		// 生成设备uuid,如果有重复id重新生成
		String uuid = PubMethod.generateUuid(FieldLimit.UUID_LEN);
		List<LoraGateway> idExisted = new ArrayList<>();
		int i = 0;
		do {
			idExisted = loraGwSrv.findLrGwByUuid(uuid);
			if (i == LOOP) {
				break;
			}
			uuid = PubMethod.generateUuid(FieldLimit.UUID_LEN);
			i++;
		} while (idExisted.isEmpty());
		return uuid;
	}

	private String generateLrDevUuid() {
		// 生成设备uuid,如果有重复id重新生成
		String uuid = PubMethod.generateUuid(FieldLimit.UUID_LEN);
		List<LoraDevice> idExisted = new ArrayList<>();
		int i = 0;
		do {
			idExisted = loraDevSrv.findLrDevByUuid(uuid);
			if (i == LOOP) {
				break;
			}
			uuid = PubMethod.generateUuid(FieldLimit.UUID_LEN);
			i++;
		} while (idExisted.isEmpty());
		return uuid;
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
		String userid = paramsJson.getString("userid");
		String mac = paramsJson.getString("devid");
		Integer type = paramsJson.getInteger("type");

		TblIotDevice dev = findDevbyUidAndMac(userid, mac);
		if (dev != null) {
			id = dev.getId();
			this.tblIotDevRepo.deleteTblIotDeviceById(id);

			this.tblIotDevBasicSrv.deleteById(id);
			this.tblIotDevDynSrv.deleteById(id);
		}
		
		//删除Lora gw
		if (type==3) {
			this.loraGwSrv.delDevByMac(mac);
		}
		
		//删除lora dev
		if (type==4) {
			this.loraDevSrv.delDevByEui(mac);
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

	public Object getDevId(JSONObject json) {
		JSONObject resultObj = new JSONObject();
		Integer type = json.getInteger("type");
		String mac = json.getString("eui");
		int num = 0;
		if (type == 3) {
			num = loraGwSrv.countGw(mac);
		} else if (type == 4) {
			num = loraDevSrv.countDev(mac);
		}
		if (num==0) {			
			return new ResultDevAdd(ResultStatusCode.DEVID_NOT_EXISTED.getCode(), ResultStatusCode.DEVID_NOT_EXISTED.getErrmsg());
		}
		
		TblIotDevice dev = getDevByMac(mac);
		if (dev==null) {
			return new ResultDevAdd(ResultStatusCode.DEVID_NOT_EXISTED.getCode(), ResultStatusCode.DEVID_NOT_EXISTED.getErrmsg()); 
		}
		String id = dev.getId();
		resultObj.put("code",0);
		resultObj.put("id", id);
		return resultObj;
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

	// public Map pageByUserid(String userid, Long page, Long pages) {
	// Long start = (page - 1) * pages;// 数据库记录编号是从0开始,这里要减1
	// List list = tblIotDevRepo.getDevsAllInfoByUserid(userid, start, pages);
	// Map<String, Object> totalMap = parseDevAllInfo(list);
	// return totalMap;
	// }

	// public Map pageByName(String userid, String search, Long page, Long
	// pages) {
	// Long start = (page - 1) * pages;
	// List list = tblIotDevRepo.getDevsAllInfoByName(userid, search, start,
	// pages);
	// Map<String, Object> totalMap = parseDevAllInfo(list);
	// return totalMap;
	// }

	public Map<String, Object> pageInfo(JSONObject paramsJson) {
		// 通过json解析参数
		String userid = paramsJson.getString("userid");
		Integer type = paramsJson.getInteger("type");
		String search = paramsJson.getString("search");
		Long page = paramsJson.getLong("page");
		if (page == null) {
			page = 1L;
		}

		Long listRows = paramsJson.getLong("listRows");

		Map<String, Object> devs = new HashMap<String, Object>();
		Long start = (page - 1) * listRows;
		List<?> list = null;
		if (type == 0) {
			// 所有
			if (listRows == null || listRows == 0) {
				list = tblIotDevRepo.getDevsAllInfoByUserid(userid, start);
			} else {
				list = tblIotDevRepo.getDevsAllInfoByUserid(userid, start, listRows);
			}
		} else if (type == 1) {
			// name
			if (listRows == null || listRows == 0) {
				tblIotDevRepo.getDevsAllInfoByName(userid, search, start);
			} else {
				list = tblIotDevRepo.getDevsAllInfoByName(userid, search, start, listRows);
			}
		} else if (type == 2) {
			// id
			if (listRows == null || listRows == 0) {
				list = tblIotDevRepo.getDevsAllInfoById(userid, search, start);
			} else {
				list = tblIotDevRepo.getDevsAllInfoById(userid, search, start, listRows);
			}
		} else if (type == 3) {
			// product
			if (listRows == null || listRows == 0) {
				list = tblIotDevRepo.getDevsAllInfoByProduct(userid, search, start);
			} else {
				list = tblIotDevRepo.getDevsAllInfoByProduct(userid, search, start, listRows);
			}
		} else if (type == 4) {
			// groupid
			if (listRows == null || listRows == 0) {
				list = tblIotDevRepo.getDevsAllInfoByGroupid(userid, search, start);
			} else {
				list = tblIotDevRepo.getDevsAllInfoByGroupid(userid, search, start, listRows);
			}
		} else if (type == 5) {
			// mac
			if (listRows == null || listRows == 0) {
				list = tblIotDevRepo.getDevsAllInfoByMac(userid, search, start);
			} else {
				list = tblIotDevRepo.getDevsAllInfoByMac(userid, search, start, listRows);
			}
		}
		Map<String, Object> totalMap = parseDevAllInfo(type, userid, search, list);
		return totalMap;
	}

	// public Map getDevsAllInfo() {
	// List devInfo = tblIotDevRepo.getDevsAllInfo();
	// Map<String, Object> totalMap = parseDevAllInfo(devInfo);
	// return totalMap;
	// }

	private Map<String, Object> parseDevAllInfo(Integer type, String userid, String search, List devInfo) {
		System.out.println("----------------parseDevAllInfo---------------");
		Map<String, Object> totalMap = new HashMap<String, Object>();
		Long number = 10L;
		if (type == 0) {
			// 当前用户下所有设备
			number = tblIotDevRepo.countByUserid(userid);
		} else if (type == 1) {
			// 设备name
			number = tblIotDevRepo.countByNameAndUserid(search, userid);
		} else if (type == 2) {
			// id
			number = tblIotDevRepo.countByIdAndUserid(search, userid);
		} else if (type == 3) {
			// product
			number = tblIotDevRepo.countByProductAndUserid(search, userid);
		} else if (type == 4) {
			// groupid
			number = tblIotDevRepo.countByGroupidAndUserid(search, userid);
		} else if (type == 5) {
			// mac
			number = tblIotDevRepo.countByMacAndUserid(search, userid);
		}

		totalMap.put("code", 0);
		totalMap.put("totalRows", number);
		ArrayList<Map> devs = new ArrayList<>();

		for (Object dev : devInfo) {
			Map<String, Object> devMap = new HashMap<String, Object>();

			System.out.println(dev);

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
			devMap.put("logintime", cells[6]);
			devMap.put("offlineTime", cells[7]);
			devs.add(devMap);
		}
		totalMap.put("devices", devs);
		return totalMap;
	}

	// 通过名称查询设备
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
