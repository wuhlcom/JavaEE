package com.zhilu.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.zhilu.demo.bean.Cat;

public interface CatRepository extends CrudRepository<Cat, Integer>{
	Cat findCatById(int id);  
    List<Cat> findCatByName(String name);  
      
    @Query(value = "select * from cat limit ?1", nativeQuery =true)  
    List<Cat> findAllCatsByCount(int count);  
}
