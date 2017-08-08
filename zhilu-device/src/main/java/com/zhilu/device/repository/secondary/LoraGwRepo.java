package com.zhilu.device.repository.secondary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.secondary.LoraGateway;

public interface LoraGwRepo extends JpaRepository<LoraGateway, String>, JpaSpecificationExecutor<LoraGateway> {
	List<LoraGateway> findLoarGatewayById(Long id);
}
