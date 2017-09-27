/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年9月18日 上午11:32:07 * 
*/
package com.zhilutec.valve.bean.params;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import com.zhilutec.valve.bean.models.TblHouseHolderData;
import com.zhilutec.valve.bean.result.HouseHolderDataResult;

public class HouseHolderDataParamSpec {

	/**
	 * 条件查询时动态组装条件 可疑盗热查询
	 */
	public static Specification<TblHouseHolderData> timeRange(final HouseHolderDataParams params) {
		return new Specification<TblHouseHolderData>() {
			@Override
			public Predicate toPredicate(Root<TblHouseHolderData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				// 获取实体属性
				Path<String> comm_address = root.get("comm_address");
				Path<Long> collect_time = root.get("collect_time");
				Path<Integer> valve_state = root.get("valve_state");

				// 阀门状态
				Predicate valveState = cb.lessThanOrEqualTo(valve_state, params.getValve_state());

				// 只返回指定的列

				// 采集时间范围,必选
				Predicate timeStart = cb.greaterThanOrEqualTo(collect_time, params.getStart_time());
				Predicate timeEnd = cb.lessThanOrEqualTo(collect_time, params.getEnd_time());
				Predicate timeRange = cb.and(timeStart, timeEnd);
				return timeRange;
			}

		};
	}

	/**
	 * 条件查询时动态组装条件 可疑盗热查询 type 0 不 分组，type 1分组 根据传入参数动态生成sql语句
	 */
	public static Specification<TblHouseHolderData> conditions(final HouseHolderDataParams params, Integer type) {
		return new Specification<TblHouseHolderData>() {
			@Override
			public Predicate toPredicate(Root<TblHouseHolderData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate restrictions = cb.conjunction();
				List<Predicate> predicates = new ArrayList<Predicate>();

				// 获取实体属性
				Path<String> comm_address = root.get("comm_address");
				Path<Integer> comm_type = root.get("comm_type");

				Path<Long> collect_time = root.get("collect_time");
				Path<Double> supply_temp = root.get("supply_temp");
				Path<Double> return_temp = root.get("return_temp");
				// Path<Integer> opening = root.get("opening");

				Long start_time = params.getStart_time();
				Long end_time = params.getEnd_time();
				Double temdif = params.getTemdif();
				String con1 = params.getCondition1();
				String con2 = params.getCondition2();
				Double wit_min = params.getWit_min();
				Double wit_max = params.getWit_max();
				Double wot_min = params.getWot_min();
				Double wot_max = params.getWot_max();
				// Short opening_status = params.getOpening();

				// System.out.println("--------------");
				// System.out.println(start_time);
				// System.out.println(end_time);
				// System.out.println(temdif);
				// System.out.println(con1);
				// System.out.println(con2);
				// System.out.println(wit_min);
				// System.out.println(wit_max);
				// System.out.println(wot_min);
				// System.out.println(wot_max);
				// System.out.println(valve_status);
				// System.out.println("--------------");

				Predicate supplyTempPre = null;
				Predicate returnTempPre = null;
				Predicate temdifPre = null;
				Predicate predicate = null;

				// 修改返回结果为指定的类
				// cb.construct(HouseHolderDataResult.class, comm_address,
				// collect_time, supply_temp,
				// return_temp);

				// 阀门状态 100 开0 关 ,不考虑开关状态
				// Predicate openingPre = cb.equal(opening, opening_status);

				// 采集时间范围,必选
				Predicate timeStart = cb.greaterThanOrEqualTo(collect_time, start_time);
				Predicate timeEnd = cb.lessThanOrEqualTo(collect_time, end_time);
				Predicate timeRangePre = cb.and(timeStart, timeEnd);

				// 实体属性列转成表达式,以用来作算术运算
				Expression<Double> supplyTem = supply_temp;
				Expression<Double> returnTem = return_temp;

				// 两列差值小于
				if (temdif != null) {
					temdifPre = cb.le(cb.diff(supplyTem, returnTem), temdif);
				}

				// 入水温度范围
				if (wit_min != null && wit_max != null) {
					Predicate witMinPre = cb.greaterThanOrEqualTo(supply_temp, wit_min);
					Predicate witMaxPre = cb.lessThanOrEqualTo(supply_temp, wit_max);
					supplyTempPre = cb.and(witMinPre, witMaxPre);
				}

				// 回水温度范围
				if (wot_min != null && wot_max != null) {
					Predicate wotMinPre = cb.greaterThanOrEqualTo(return_temp, wot_min);
					Predicate wotMaxPre = cb.lessThanOrEqualTo(return_temp, wot_max);
					returnTempPre = cb.and(wotMinPre, wotMaxPre);
				}

				// ------------------- 三组参数都有值--------------------------
				if (supplyTempPre != null && returnTempPre != null && temdifPre != null) {
					// and or
					if (con1 != null && con1.toLowerCase().equals("and") && con2 != null
							&& con2.toLowerCase().equals("or")) {
						Predicate temPre = cb.and(supplyTempPre, returnTempPre);
						predicate = cb.or(temdifPre, (temPre));
						// or and
					} else if (con1 != null && con1.toLowerCase().equals("or") && con2 != null
							&& con2.toLowerCase().equals("and")) {
						Predicate temPre = cb.or(supplyTempPre, returnTempPre);
						predicate = cb.and(temdifPre, (temPre));
						// or or
					} else if (con1 != null && con1.toLowerCase().equals("or") && con2 != null
							&& con2.toLowerCase().equals("or")) {
						Predicate temPre = cb.or(temdifPre, returnTempPre);
						predicate = cb.or(supplyTempPre, temPre);
						// and and
					} else {
						Predicate temPre = cb.and(supplyTempPre, returnTempPre);
						predicate = cb.and(temdifPre, (temPre));
					}
					// ------------------------------------
					// 两组参数有值-------------------
				} else if (supplyTempPre != null && returnTempPre != null && temdifPre == null) {
					// 只有supplyTempPre和returnTempPre
					// or
					if (con1 != null && con1.toLowerCase().equals("or")) {
						predicate = cb.or(supplyTempPre, returnTempPre);
						// and
					} else {
						predicate = cb.and(supplyTempPre, returnTempPre);
					}
				} else if (supplyTempPre != null && returnTempPre == null && temdifPre != null) {
					// 只有supplyTempPre和temdifPre
					// or
					if (con2 != null && con2.toLowerCase().equals("or")) {
						predicate = cb.or(supplyTempPre, temdifPre);
						// and
					} else {
						predicate = cb.and(supplyTempPre, temdifPre);
					}
				} else if (supplyTempPre != null && returnTempPre == null && temdifPre != null) {
					// 只有returnTempPre和temdifPre
					// or
					if (con2 != null && con2.toLowerCase().equals("or")) {
						predicate = cb.or(returnTempPre, temdifPre);
						// and
					} else {
						predicate = cb.and(returnTempPre, temdifPre);
					}
					// ---------------------------一组参数有值----只取and---------------
				} else if (supplyTempPre != null && returnTempPre == null && temdifPre == null) {
					// 只有supplyTempPre
					predicate = supplyTempPre;
				} else if (supplyTempPre == null && returnTempPre != null && temdifPre == null) {
					// 有returnTempPre 与前面
					predicate = returnTempPre;
				} else if (supplyTempPre == null && returnTempPre == null && temdifPre != null) {
					predicate = temdifPre;
				}

				if (predicate != null) {
					predicate = cb.and(predicate, timeRangePre);
				} else {
					System.out.println("only timeRangePre");
					predicate = cb.and(timeRangePre);
				}

				// System.out.println(predicate);
				if (type == 1) {
					query.groupBy(comm_address, comm_type);
					return query.where(predicate).getRestriction();
				} else {
					return predicate;
				}

			}

		};
	}

	public static Specification<TblHouseHolderData> devSearchSpec(String uid, Integer type, String search) {
		return new Specification<TblHouseHolderData>() {
			@Override
			public Predicate toPredicate(Root<TblHouseHolderData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> uidExp = root.get("comm_address");
				Path<Integer> typeExp = root.get("type");
				Path<String> searchExp = root.get("name");
				Predicate predicate = cb.and(cb.equal(uidExp, uid), cb.equal(typeExp, type),
						cb.like(searchExp, search));
				return predicate;
			}
		};
	}
}
