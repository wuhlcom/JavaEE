package com.zhilu.device.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zhilu.device.bean.TblIotDevice;

public class PubMethod {
	static int[] PROTOCOL = { 0, 1, 2, 3 };

	/**
	 * 判断协议是否确
	 */
	public static boolean isProtocol(int protocol) {
		boolean isExist = false;
		for (int i = 0; i < PROTOCOL.length; i++) {
			if (PROTOCOL[i] == protocol) {
				isExist = true;
			}
		}
		return isExist;
	}

	/**
	 * 判断添加设备参数是否正确
	 * 
	 * @param tblIotDeviceEntity
	 * @return
	 */
	public static Map<Integer, String> paramJudge(TblIotDevice tblIotDeviceEntity) {
		String userid = tblIotDeviceEntity.getUserid();
		String name = tblIotDeviceEntity.getName();
		String product = tblIotDeviceEntity.getProduct();
		int protocol = tblIotDeviceEntity.getProtocol();
		String devices = tblIotDeviceEntity.getId();
		Map<Integer, String> map = new HashMap<Integer, String>();
		int code = 3002;
		String error;
		// List<String> list = new ArrayList<String>();
		// list.add("error");

		if (userid == null || userid.length() <= 0) {
			error = "userid error, userid:" + userid;
			map.put(code, error);
		} else if (name == null || name.length() <= 0) {
			error = "name error, name:" + name;
			map.put(code, error);
		} else if (product == null || product.length() <= 0) {
			error = "product error,product:" + product;
			map.put(code, error);
		} else {
			boolean b = PubMethod.isProtocol(protocol);
			if (!b) {
				error = "protocol error,protocol:" + protocol;
				map.put(code, error);
			} else if (devices == null || devices.length() <= 0) {
				error = "id error,id:" + devices;
				map.put(code, error);
			}
		}
		;
		return map;
	}

	/**
	 * @param tblIotDeviceEntity
	 *            返回设备Id
	 */
	public static ArrayList<String> getDevId(TblIotDevice tblIotDeviceEntity) {
		String devId = tblIotDeviceEntity.getId();
		ArrayList<String> listDevIds = new ArrayList<>();
		listDevIds.add(devId);
		return listDevIds;
	}

	/*
	public void JpaSpecificationExecutor(TblIotDeviceRepository tblIotDeviceRepository){
		int pageNo = 3 - 1;
		int pageSize = 5;
		PageRequest pageable = new PageRequest(pageNo, pageSize);
		
		//通常使用 Specification 的匿名内部类
		Specification<TblIotDevice> specification = new Specification<TblIotDevice>() {
			**
			 * @param *root: 代表查询的实体类. 
			 * @param query: 可以从中可到 Root 对象, 即告知 JPA Criteria 查询要查询哪一个实体类. 还可以
			 * 来添加查询条件, 还可以结合 EntityManager 对象得到最终查询的 TypedQuery 对象. 
			 * @param *cb: CriteriaBuilder 对象. 用于创建 Criteria 相关对象的工厂. 当然可以从中获取到 Predicate 对象
			 * @return: *Predicate 类型, 代表一个查询条件. 
			 
			@Override
			public Predicate toPredicate(Root<TblIotDevice> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path path = root.get("id");
				Predicate predicate = cb.gt((path, 5);
				return predicate;
			}
		};
		
		Page<TblIotDevice> page = tblIotDeviceRepository.findAll(specification, pageable);
		
		System.out.println("总记录数: " + page.getTotalElements());
		System.out.println("当前第几页: " + (page.getNumber() + 1));
		System.out.println("总页数: " + page.getTotalPages());
		System.out.println("当前页面的 List: " + page.getContent());
		System.out.println("当前页面的记录数: " + page.getNumberOfElements());
	}
	*/

}
