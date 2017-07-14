package com.zhilu.demo.respository;

import org.springframework.data.repository.CrudRepository;

import com.zhilu.demo.bean.Cat;

public interface CatRepository extends CrudRepository<Cat, Integer>{

}
