package com.dazk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;
import com.dazk.common.util.ParamValidator;
import com.dazk.common.util.PubUtil;
import com.dazk.db.model.Menu;
import com.dazk.service.MenuService;
import com.dazk.validator.FieldLimit;
import com.dazk.validator.JsonParamValidator;
import com.dazk.validator.MenuValidator;

@RestController
@RequestMapping("/menu")
public class MenuController {
	@Resource
	private MenuService menuService;

	@RequestMapping(value = "/addMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addMenu(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		// 权限验证
		String token = request.getParameter("token");
		// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
		try {
			System.out.println("result=" + requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			// 数据校验
			if (!MenuValidator.menuVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = menuService.addMenu(parameter);
			if (res == 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(),
						ResultStatusCode.REPETITION_ERR.getErrmsg());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), ResultStatusCode.ROUTINE_ERR.getErrmsg());
		} finally {

		}
		return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
	}
	
	@RequestMapping(value = "/delMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object delMenu(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

			JSONObject parameter = JSON.parseObject(requestBody);
			if (!MenuValidator.isMenuCode(parameter.getString("code"),FieldLimit.MENU_CODE_MIN,FieldLimit.MENU_CODE_MAX)) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "非法菜单编号");
			}
			// 数据入库，成功后返回.
			int res = menuService.delMenu(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "删除时程序出错");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "删除数据不存在");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), ResultStatusCode.ROUTINE_ERR.getErrmsg());
		}
	}

	@RequestMapping(value = "/updateMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object updateMenu(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
		
			JSONObject parameter = JSON.parseObject(requestBody);
			// 数据校验
			if (!MenuValidator.menuVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = menuService.updateMenu(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "更新时程序出错");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "要更新的数据不存在");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), ResultStatusCode.ROUTINE_ERR.getErrmsg());
		}
	}
	

	@RequestMapping(value = "/queryMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryMenu(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);

			if (!MenuValidator.menuQueryVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}
			
			// 数据查询，成功后返回.
			List<Menu> result = menuService.queryMenu(parameter);
			for (int i = 0; i < result.size(); i++) {
				result.get(i).setIsdel(null);
				result.get(i).setCreated_at(null);
				result.get(i).setListRows(null);
				result.get(i).setPage(null);
			}
			int totalRows = menuService.queryMenuCount(parameter);
			resultObj.put("errcode", ErrCode.success);
			resultObj.put("totalRows", totalRows);
			resultObj.put("result", result);
			return resultObj.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询菜单时出现异常");
		}
	}

}
