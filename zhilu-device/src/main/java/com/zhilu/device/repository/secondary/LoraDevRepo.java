package com.zhilu.device.repository.secondary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhilu.device.bean.secondary.LoraDevice;

public interface LoraDevRepo extends JpaRepository<LoraDevice, String>, JpaSpecificationExecutor<LoraDevice> {
	List<LoraDevice> findLoraDeviceById(Long id);
}
