package com.zhilu.device.repository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhilu.device.bean.TblIotDevice;

public interface TblIotDevRepo extends JpaRepository<TblIotDevice, String>, JpaSpecificationExecutor<TblIotDevice> {

	List<TblIotDevice> findTblIotDeviceById(String id);

	List<TblIotDevice> getTblIotDeviceById(String id);

	List<TblIotDevice> findTblIotDeviceByMac(String mac);

	List<TblIotDevice> getTblIotDeviceByMac(String mac);

	List<TblIotDevice> findTblIotDeviceByName(String name);

	// userid
	List<TblIotDevice> findTblIotDeviceByUserid(String userid);

	TblIotDevice findTblIotDeviceByUseridAndMac(String userid, String mac);

	TblIotDevice findTblIotDeviceByUseridAndId(String userid, String id);

	void deleteTblIotDeviceById(String id);

	// count *
	// @Query(" select count(t) from FollowerInfo t where investUserId =
	// :invUserId")
	@Query(" select count(d) from TblIotDevice d")
	Long getDevTotalNum();

	// count by id
	Long countById(Long id);

	// limit
	// SELECT a FROM foo WHERE b = 1 LIMIT 100,10;
	// @Query(value = "select count(id) from tbl_tot_device limit
	// :start,:showRows", nativeQuery = true)
	// Long getDevByPage(@Param("start") Long start, @Param("showRows") Long
	// showRows);

	// 按名称模糊查询
	// @Query("SELECT p FROM Person p WHERE p.lastName LIKE %:lastName% OR
	// p.email LIKE %:email%")
	@Query("SELECT dev FROM TblIotDevice dev WHERE dev.name like %:name%)")
	List<TblIotDevice> getDevsByName(@Param("name") String name);

	// 使用原生sql
	// @Query(value = "select * from tbl_tot_device limit ?1", nativeQuery =
	// true)
	// List<TblIotDevice> getAllTblIotDevicesByCount(int count);

	// 两表联表查询并分页
	// @Query("select dev.name,dev.id,dev.productid from TblIotDevice dev inner
	// join dev.tblBasic basic where basic.deviceid =:id")
	// List<Object> getDevAndBasic(@Param("id") String id);
	// 通过userid
	@Query(value = "select dev.id,dev.mac,dev.name,dev.product,dev.protocol,basic.status,basic.logintime,basic.offlinetime	from tbl_iot_device as dev	INNER JOIN tbl_iot_device_basic as basic on  dev.id =basic.deviceid where (dev.userid = :userid) limit :start,:pages", nativeQuery = true)
	List<Object[]> getDevsAllInfoByUserid(@Param("userid") String userid, @Param("start") Long start,
			@Param("pages") Long pages);

	// 通过name
	@Query(value = "select dev.id,dev.mac,dev.name,dev.product,dev.protocol,basic.status,basic.logintime,basic.offlinetime	from tbl_iot_device as dev	INNER JOIN tbl_iot_device_basic as basic on  dev.id =basic.deviceid where (dev.userid = :userid and dev.name like %:name%) limit :start,:pages", nativeQuery = true)
	List<Object[]> getDevsAllInfoByName(@Param("userid") String userid, @Param("name") String name,
			@Param("start") Long start, @Param("pages") Long pages);

	// 通过id
	@Query(value = "select dev.id,dev.mac,dev.name,dev.product,dev.protocol,basic.status,basic.logintime,basic.offlinetime	from tbl_iot_device as dev	INNER JOIN tbl_iot_device_basic as basic on  dev.id =basic.deviceid INNER JOIN tbl_iot_device_dyn as dyn on  dev.id =dyn.deviceid where (dev.userid = :userid and dev.id like %:id%) limit :start,:pages", nativeQuery = true)
	List<Object[]> getDevsAllInfoById(@Param("userid") String userid, @Param("id") String id,
			@Param("start") Long start, @Param("pages") Long pages);

	// 通过Product
	@Query(value = "select dev.id,dev.mac,dev.name,dev.product,dev.protocol,basic.status,basic.logintime,basic.offlinetime	from tbl_iot_device as dev	INNER JOIN tbl_iot_device_basic as basic on  dev.id =basic.deviceid where (dev.userid = :userid and dev.product like %:product%) limit :start,:pages", nativeQuery = true)
	List<Object[]> getDevsAllInfoByProduct(@Param("userid") String userid, @Param("product") String product,
			@Param("start") Long start, @Param("pages") Long pages);

	// 通过Group
	@Query(value = "select dev.id,dev.mac,dev.name,dev.product,dev.protocol,basic.status,basic.logintime,basic.offlinetime	from tbl_iot_device as dev	INNER JOIN tbl_iot_device_basic as basic on  dev.id =basic.deviceid where (dev.userid = :userid and dev.groupid=:groupid) limit :start,:pages", nativeQuery = true)
	List<Object[]> getDevsAllInfoByGroupid(@Param("userid") String userid, @Param("groupid") String group,
			@Param("start") Long start, @Param("pages") Long pages);

	// 通过Mac
	@Query(value = "select dev.id,dev.mac,dev.name,dev.product,dev.protocol,basic.status,basic.logintime,basic.offlinetime	from tbl_iot_device as dev	INNER JOIN tbl_iot_device_basic as basic on  dev.id =basic.deviceid where (dev.userid = :userid and dev.mac=:mac) limit :start,:pages", nativeQuery = true)
	List<Object[]> getDevsAllInfoByMac(@Param("userid") String userid, @Param("mac") String mac,
			@Param("start") Long start, @Param("pages") Long pages);

	@Query("select dev.name from TblIotDevice dev inner join dev.tblDyn dyn where dyn.deviceid =:id")
	List<Object> getDevAndDyn(@Param("id") String id);

	// value = "select
	// dev.id,dev.mac,dev.name,dev.product,dev.protocol,basic.status,basic.logintime,basic.offlinetime
	// from tbl_iot_device as dev,tbl_iot_device_basic as basic
	// where (dev.id = basic.deviceid and dev.userid =basic.userid) limit 1,10"

	// 三表联表查询
	// select application FROM Application a
	// join a.customer c
	// join c.users u
	// where u.id = :userId
	@Query("select dev.name,dev.userid,dev.id,dev.protocol,dev.productid,dev.mac,basic.status,dyn.onlinetime from TblIotDevice dev join dev.tblBasic basic join dev.tblDyn dyn where basic.deviceid =:id and  dyn.deviceid =:id")
	List<Object[]> getDevsAllInfoById(@Param("id") String id);

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
	@Query("select dev.id,dev.mac,dev.name,dev.product,dev.protocol,basic.status,basic.logintime,basic.offlinetime from TblIotDevice dev join dev.tblBasic basic join dev.tblDyn dyn where basic.deviceid =dev.id and  dyn.deviceid =dev.id")
	List<Object[]> getDevsAllInfo();

	// 根据uid查询并限制显示数量
	@Query(value = "select dev.id,dev.mac,dev.name,dev.product,dev.protocol,basic.status,basic.logintime,basic.offlinetime from tbl_iot_device as dev,tbl_iot_device_basic as basic,tbl_iot_device_dyn as dyn	where dev.id = basic.deviceid and dev.id =dyn.deviceid limit ?1,?2", nativeQuery = true)
	List<Object[]> getDevsAllInfoByUid(Long Start, Long showRows);

	// @Query("select dev,basic,dyn from TblIotDevice dev join dev.tblBasic
	// basic join dev.tblDyn dyn where basic.deviceid =:id and dyn.deviceid
	// =:id")
	// ArrayList<TblIotDevice> getDevAllInfo2(@Param("id") String id);

	// @Query(value="select dev.name,dev.id,dev.mac,dev.protocol,basic.status
	// from tbl_iot_device as dev,tbl_iot_device_basic as
	// basic,tbl_iot_device_dyn as dyn where dev.id = basic.deviceid and dev.id
	// =dyn.deviceid where id=:id",nativeQuery=true)
	// List<Object[]> getDevAllInfo3(@Param("id") String id);
	// @Query(value="select dev.name,dev.id,dev.mac,dev.protocol,basic.status
	// from tbl_iot_device as dev,tbl_iot_device_basic as
	// basic,tbl_iot_device_dyn as dyn where dev.id = basic.deviceid and dev.id
	// =dyn.deviceid",nativeQuery=true)
	// List<Object[]> getDevAllInfo3();

	// 根据id与uid删除记录
	@Modifying
	@Query("delete from TblIotDevice where id = :id and userid = :userid")
	void deleteByUseridAndId(@Param("userid") String userid, @Param("id") String id);

	@Modifying
	@Query("delete from TblIotDevice d where d.mac = :mac and d.userid = :userid")
	void deleteByUserAndMac(@Param("userid") String userid, @Param("mac") String mac);

	// 修改设备名字
	@Modifying(clearAutomatically = true)
	@Query("update TblIotDevice d set d.name = :name  where d.userid = :userid and d.mac=:mac")
	TblIotDevice setDevName(@Param("name") String name, @Param("userid") String userid, @Param("mac") String mac);

	// 修改设备协议类型
	@Modifying(clearAutomatically = true)
	@Query("update TblIotDevice d set d.protocol = :protocol  where d.userid = :userid and d.mac=:mac")
	TblIotDevice setDevProtocol(@Param("protocol") int protocol, @Param("userid") String userid,
			@Param("mac") String mac);

	// 修改设备所属产品类型
	@Modifying
	@Query("update TblIotDevice d set d.productid = :productid  where d.userid = :userid and d.mac=:mac")
	TblIotDevice setDevProductid(@Param("productid") String productid, @Param("userid") String userid,
			@Param("mac") String mac);

	// 修改设备名字，协议,产品类型
	@Modifying(clearAutomatically = true)
	@Query("update TblIotDevice d set d.name = :name, d.protocol=:protocol, d.productid= :productid where d.userid = :userid and d.mac=:mac")
	TblIotDevice setDevInfo(@Param("name") String name, @Param("protocol") int protocol,
			@Param("productid") String productid, @Param("userid") String userid, @Param("mac") String mac);

	// // 设置 nativeQuery=true 即可以使用原生的 SQL 查询
	// @Query(value = "SELECT count(id) FROM jpa_persons", nativeQuery = true)
	// long getTotalCount();
	//
	// // 可以通过自定义的 JPQL 完成 UPDATE 和 DELETE 操作. 注意: JPQL 不支持使用 INSERT
	// // 在 @Query 注解中编写 JPQL 语句, 但必须使用 @Modifying 进行修饰. 以通知 SpringData, 这是一个
	// // UPDATE 或 DELETE 操作
	// // UPDATE 或 DELETE 操作需要使用事务, 此时需要定义 Service 层. 在 Service 层的方法上添加事务操作.
	// // 默认情况下, SpringData 的每个方法上有事务, 但都是一个只读事务. 他们不能完成修改操作!
	// @Modifying
	// @Query("UPDATE Person p SET p.email = :email WHERE id = :id")
	// void updatePersonEmail(@Param("id") Integer id, @Param("email") String
	// email);
}
