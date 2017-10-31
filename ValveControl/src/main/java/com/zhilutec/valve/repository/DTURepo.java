package com.zhilutec.valve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alibaba.fastjson.JSONArray;
import com.zhilutec.valve.bean.models.DTU;

public interface DTURepo extends JpaRepository<DTU, String>, JpaSpecificationExecutor<DTU>{
	@Query("select bv from DTU bv where bv.comm_address in :comm_addresses")
	List<DTU> getValves(@Param(value = "comm_addresses") JSONArray comm_addresses);
}
