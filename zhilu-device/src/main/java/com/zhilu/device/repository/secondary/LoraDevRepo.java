package com.zhilu.device.repository.secondary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhilu.device.bean.secondary.LoraDevice;

public interface LoraDevRepo extends JpaRepository<LoraDevice, String>, JpaSpecificationExecutor<LoraDevice> {
	public List<LoraDevice> findLoraDeviceById(Long id);	
	public List<LoraDevice> findLoraDeviceByUuid(String uuid);
	
	public int countLoraDeviceByDeveui(String eui);
	
	@Modifying
	@Query("delete from LoraDevice where id = :id and app_id = :app_id")
	void deleteByAppidAndId(@Param("app_id") Integer app_id, @Param("id") Integer id);
	
	@Modifying
	@Query("delete from LoraDevice where deveui = :deveui")
	void deleteByDeveui(@Param("deveui") String deveui);
}
