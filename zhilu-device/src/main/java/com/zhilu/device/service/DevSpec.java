package com.zhilu.device.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.zhilu.device.bean.TblIotDevice;
import com.zhilu.device.bean.TblIotDeviceBasic;
import com.zhilu.device.bean.TblIotDeviceDyn;

public class DevSpec {

	// 1.混合条件查询
	/*
	 * Path<String> exp1 = root.get("taskName"); Path<Date> exp2 =
	 * root.get("createTime"); Path<String> exp3 = root.get("taskDetail");
	 * Predicate predicate = cb.and(cb.like(exp1,
	 * "%taskName%"),cb.lessThan(exp2, new Date())); return
	 * cb.or(predicate,cb.equal(exp3, "kkk"));
	 * 
	 * 类似的sql语句为: Hibernate: select count(task0_.id) as col_0_0_ from tb_task
	 * task0_ where ( task0_.task_name like ? ) and task0_.create_time<? or
	 * task0_.task_detail=?
	 */	
	public static Specification<TblIotDevice> devSearchSpec(String uid, Integer type, String search) {
		return new Specification<TblIotDevice>() {
			@Override
			public Predicate toPredicate(Root<TblIotDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> uidExp = root.get("userid");
				Path<Integer> typeExp = root.get("type");
				Path<String> searchExp = root.get("name");		
				Predicate predicate = cb.and(cb.equal(uidExp, uid), cb.equal(typeExp, type),
						cb.like(searchExp, search));
				return predicate;
			}
		};
	}

	public static Specification<TblIotDevice> devSearchSpec(String uid, Integer type) {

		return new Specification<TblIotDevice>() {
			@Override
			public Predicate toPredicate(Root<TblIotDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> uidExp = root.get("userid");
				Path<Integer> typeExp = root.get("type");					
				Predicate predicate = cb.and(cb.equal(uidExp, uid), cb.equal(typeExp, type));
				return predicate;
			}

		};
	}

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
	public static Specification<TblIotDevice> multTable() {

		return new Specification<TblIotDevice>() {
			@Override
			public Predicate toPredicate(Root<TblIotDevice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				 Join<TblIotDevice,TblIotDeviceBasic> join = root.join("TblIotDeviceBasic", JoinType.INNER);
				  Path<String> exp4 = join.get("tblBasic"); return cb.like(exp4,
				  "%tblBasic%");
			}

		};
	}

}