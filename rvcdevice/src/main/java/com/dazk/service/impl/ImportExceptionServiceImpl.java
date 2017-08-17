package com.dazk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dazk.db.dao.ImportExceptionMapper;
import com.dazk.db.model.ImportException;
import com.dazk.service.ImportExceptionService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;



@Service
public class ImportExceptionServiceImpl implements ImportExceptionService{
	
	@Autowired
	private ImportExceptionMapper importExceptionMapper;

	@Override
	public void insert(ImportException importException) {
		importExceptionMapper.insertSelective(importException);
	}

	@Override
	public void insertList(List<ImportException> list) {
		importExceptionMapper.insertList(list);
	}

	@Override
	public List<ImportException> getImportExceptions(Example example) {
		return importExceptionMapper.selectByExample(example);
	}

	@Override
	public int countImportException(Example example) {
		return importExceptionMapper.selectCountByExample(example);
	}

	@Override
	public List<ImportException> getImportExceptions(ImportException importException, Example example) {
		if (importException.getPage() != null && importException.getListRows() != null) {
            PageHelper.startPage(importException.getPage(), importException.getListRows());
        }
		return importExceptionMapper.selectByExample(example);
	}
	
}
