package com.zhilu.device.repository.secondary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zhilu.device.bean.secondary.LoraGateway;

public interface LoraGwRepo extends JpaRepository<LoraGateway, String>, JpaSpecificationExecutor<LoraGateway> {
	public List<LoraGateway> findLoraGatewayById(Long id);
	public List<LoraGateway> findLoraGatewayByUuid(String uuid);
	public List<LoraGateway> findLoraGatewayByMac(String mac);
	
	public int countLoraGatewayById(Long id);
	public int countLoraGatewayByMac(String mac);
		
	@Modifying
	@Query("delete from LoraGateway where mac = :mac and userid = :userid")
	void deleteByAppidAndId(@Param("mac") String mac, @Param("userid") String userid);
	
	@Modifying
	@Query("delete from LoraGateway where mac = :mac")
	void deleteByMac(@Param("mac") String mac);
}
