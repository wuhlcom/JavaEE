package fish.yu.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import fish.yu.dao.MyPermissionInitMapper;
import fish.yu.dao.PermissionMapper;
import fish.yu.entity.FrontPage;
import fish.yu.entity.MyPermissionInit;



/**
 * 
 * @author yuliang-ds1
 *
 */
@Service
public class PermissionInitService extends ServiceImpl<MyPermissionInitMapper, MyPermissionInit> {
	
	
	@Autowired
	MyPermissionInitMapper myPermissionInitMapper;
	
	/**
	 * 查询所有在线用户
	 * @param frontPage
	 * @return
	 */
	public Page<MyPermissionInit> queryAllPermissions(FrontPage<MyPermissionInit> frontPage) {
		
		int rows = frontPage.getRows();
		int currentPage = frontPage.getPage();
		String sord = frontPage.getSord();
		boolean ascFlag=true;
		if(!"asc".equals(sord)){
			ascFlag=false;
		}
		Pagination page =new Pagination();
		page.setSize(rows);
		page.setCurrent(currentPage);
		page.setAsc(ascFlag);
		if(StringUtils.isNotEmpty(frontPage.getSidx())){
			page.setOrderByField(frontPage.getSidx());
		}else{
			page.setOrderByField("sort");
		}
		
		//Page  page=new Page(1,5,"id");
		
		List<MyPermissionInit> initPermissionList = myPermissionInitMapper.selectPage(page, Condition.instance());
		
		Page<MyPermissionInit> pageList = frontPage.getPagePlus();
		if(CollectionUtils.isNotEmpty(initPermissionList)){
		
			pageList.setRecords(initPermissionList);
			pageList.setTotal(initPermissionList.size());
			
		}
		return pageList;
	}
	
}
