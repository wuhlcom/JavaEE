//package com.zhilu.device.repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import com.zhilu.device.bean.TblIotDevice;
////自定义实现Rository接口
//public class TblIotDeviceRepositoryImpl implements TblIotDeviceDao {
//	@PersistenceContext
//	private EntityManager entityManager;
//	
//	@Override
//	public void test(){	
//		TblIotDevice dev= entityManager.find(TblIotDevice.class,"dddd");
//		System.out.println(dev);
//	}
//}
