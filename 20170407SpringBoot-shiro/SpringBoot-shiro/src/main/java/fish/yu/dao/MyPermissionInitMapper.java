package fish.yu.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import fish.yu.entity.MyPermissionInit;

/**
 * 
 * @author yuliang-ds1
 *
 */
public interface MyPermissionInitMapper extends BaseMapper<MyPermissionInit> {

	List<MyPermissionInit> selectAll();

}