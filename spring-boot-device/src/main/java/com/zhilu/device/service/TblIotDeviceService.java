package com.zhilu.device.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zhilu.device.repository.TblIotDeviceRepository;
import com.zhilu.device.bean.TblIotDevice;;

@Service
public class TblIotDeviceService {	
	    @Autowired
	    private TblIotDeviceRepository tblIotDevicePage;
	    
	    public Page<TblIotDevice> getTblIotDevice(int pageNumber,int pageSize){
	        PageRequest request = this.buildPageRequest(pageNumber,pageSize);
	        Page<TblIotDevice> tblIotDevices= this.tblIotDevicePage.findAll(request);
	        return tblIotDevices;
	    }
        
	    //构建PageRequest
        private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
	        return new PageRequest(pageNumber - 1, pagzSize, null);
	    }

}
