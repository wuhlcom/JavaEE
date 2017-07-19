package com.zhilu.device.service;

import java.util.ArrayList;
import java.util.List;

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

import com.zhilu.device.repository.TblIotDeviceRepository;
import com.zhilu.device.bean.TblIotDevice;

@Service
public class TblIotDeviceService {
	@Autowired
	private TblIotDeviceRepository tblIotDevRepo;

	//创建设备组
	public ArrayList<?> addDevices(String userid, String devname, String product, Integer protocol, String[] ids) {
		List<TblIotDevice> devicesobj = new ArrayList<>();
		for (String id : ids) {
			TblIotDevice device = new TblIotDevice();
			device.setUserid(userid);
			device.setProduct(product);
			device.setProtocol(protocol);
			device.setId(id);
			devicesobj.add(device);
		}
		ArrayList<?> devids =saveDevices(devicesobj);
		return devids;
	}

	//添加设备组
	//返回设备ID数组
	@Transactional
	public ArrayList<String> saveDevices(List<TblIotDevice> devices) {
		List<TblIotDevice> rsSave = tblIotDevRepo.save(devices);
		ArrayList<String> devids=new ArrayList<>();
		for (TblIotDevice device : rsSave) {
			devids.add(device.getId());
		}
		return devids;
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
