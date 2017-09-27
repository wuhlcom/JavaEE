package com.dazk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.errcode.Result;
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;

import com.dazk.common.util.PubUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.model.Menu;
import com.dazk.service.MenuService;
import com.dazk.service.RolePermissionService;
import com.dazk.validator.FieldLimit;
import com.dazk.validator.MenuValidator;
import com.dazk.validator.TokenValidator;

@RestController
@RequestMapping("/menu")
public class MenuController {
	public final static Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Resource
	private MenuService menuService;

	@Resource
	private RolePermissionService rolePermiService;

	@RequestMapping(value = "/addMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addMenu(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {

		try {
			logger.debug("Request=" + requestBody);
			// 权限验证
			// post head token
			String token = request.getHeader("token");
			// String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("user_id", rsToken.getString("userid"));
			String roleId = parameter.getString("role_id");
			if (RegexUtil.isNull(roleId)) {
				parameter.put("role_id", rsToken.getString("role"));
			}
			// 数据校验
			ResultErr paramsVal =MenuValidator.menuVal(parameter);
			if (paramsVal.getErrcode()!=10001) {
				// 非法数据，返回错误码
				return paramsVal;
			}

			// 数据入库，成功后返回.
			int res = menuService.addMenu(parameter);
			if (res == 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(),
						"菜单已经存在!");
			}else if (res == -2) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(),
						"URI已经存在!");
			}else if (res == -3) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(),
						"菜单前端路由已经存在!");
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
			logger.debug("Request=" + requestBody);

			// 权限验证
			// String token = request.getParameter("token");
			// post head token
			String token = request.getHeader("token");
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("user_id", rsToken.getString("userid"));
			if (!MenuValidator.isMenuId(parameter.getString("id"))) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "非法菜单ID");
			}
			// 数据入库，成功后返回.
			int res = menuService.delMenu(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "删除菜单出错");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "删除的菜单不存在");
			} else if (res ==-2){
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(), "当前菜单有子菜单无法直接删除");
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
			logger.debug("Request=" + requestBody);
			/// 权限验证
			// post head token
			String token = request.getHeader("token");
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("user_id", rsToken.getString("userid"));
			// 数据校验			
			ResultErr paramsVal =MenuValidator.menuUpdateVal(parameter);
			if (paramsVal.getErrcode()!=10001) {
				// 非法数据，返回错误码
				return paramsVal;
			}

			// 数据入库，成功后返回.
			int res = menuService.updateMenu(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "更新菜单出错");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "要更新的菜单不存在");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), ResultStatusCode.ROUTINE_ERR.getErrmsg());
		}
	}

	@RequestMapping(value = "/queryMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryMenu(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {

			logger.debug("Request=" + requestBody);
			// 权限验证
			// post head token
			String token = request.getHeader("token");
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("user_id", rsToken.getString("userid"));

			if (!MenuValidator.menuQueryVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据查询，成功后返回.
			Integer type = parameter.getInteger("type");
			if (type == 0 || type == 1 || type == 2) {
				List<Menu> result = menuService.queryMenu(parameter);
				for (int i = 0; i < result.size(); i++) {
					Menu menu =result.get(i);
					menu.setIsdel(null);
					menu.setCode(null);
					menu.setRole_id(null);
					menu.setCreated_at(null);
					menu.setListRows(null);
					menu.setPage(null);
				}

				int totalRows = menuService.queryMenuCount(parameter);
				resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
				resultObj.put("totalRows", totalRows);
				resultObj.put("result", result);
			} else {
				List<?> result = menuService.queryMenu(parameter);
				resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
				resultObj.put("result", result);
				return resultObj;
			}
			return resultObj.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询菜单时出现异常");
		}
	}

}
