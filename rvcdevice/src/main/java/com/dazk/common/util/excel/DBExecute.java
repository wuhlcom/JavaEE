package com.dazk.common.util.excel;

import java.util.List;

public interface DBExecute {

//	int NUMBER = Integer.valueOf(PropertyUtils.getProperty("EXECUTE_NUMBER"));
	int NUMBER = 1;
	
	<T> String execute(List<T> list);

}
