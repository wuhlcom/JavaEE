package com.dazk.service;

import java.util.List;

import com.dazk.db.model.ImportException;

import tk.mybatis.mapper.entity.Example;

public interface ImportExceptionService {
	
	public void insert(ImportException importException);
	
	public void insertList(List<ImportException> list);
	
	public List<ImportException> getImportExceptions(Example example);
	
	public int countImportException(Example example);
	
	public List<ImportException> getImportExceptions(ImportException importException,Example example);
	
}
