/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午9:21:11 * 
*/
package com.dazk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.ErrCode;
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;
import com.dazk.common.util.PubFunction;
import com.dazk.db.model.Role;
import com.dazk.db.model.RolePermission;
import com.dazk.service.RolePermissionService;
import com.dazk.service.RoleService;
import com.dazk.validator.JsonParamValidator;

@Controller
@RestController
@RequestMapping("/role")
public class RoleController {

	@Resource
	private RoleService roleService;

	@Resource
	private RolePermissionService rolePermiService;

	@RequestMapping(value = "/addRole", method = RequestMethod.POST, produces = PubFunction.DATA_CODE)
	public Object addRole(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			System.out.println("result=" + requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			// 数据校验
			if (!JsonParamValidator.roleVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = roleService.addRole(parameter);
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

	@RequestMapping(value = "/updateRole", method = RequestMethod.POST, produces = PubFunction.DATA_CODE)
	public Object updateRole(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

			JSONObject parameter = JSON.parseObject(requestBody);
			// 数据校验
			if (!JsonParamValidator.roleVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = roleService.updateRole(parameter);
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
	
	@RequestMapping(value = "/delRole", method = RequestMethod.POST, produces = PubFunction.DATA_CODE)
	public Object delrole(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

			JSONObject parameter = JSON.parseObject(requestBody);
			if (!JsonParamValidator.isCode(parameter.getString("code"),JsonParamValidator.ROLE_CODE_MIN,JsonParamValidator.ROLE_CODE_MAX)) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "非法角色编号");
			}
			// 数据入库，成功后返回.
			int res = roleService.delRole(parameter);
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
	
	@RequestMapping(value = "/queryRole", method = RequestMethod.POST, produces = PubFunction.DATA_CODE)
	public Object queryRole(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println(requestBody);
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);

			// 数据校验
			String code=parameter.getString("code");
			if (!JsonParamValidator.roleQueryVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}
		
			// 数据查询，成功后返回.
			List<Role> result = roleService.queryRole(parameter);
			for (int i = 0; i < result.size(); i++) {
				result.get(i).setDisused(null);
				result.get(i).setCreated_at(null);
				result.get(i).setListRows(null);
				result.get(i).setPage(null);
			}	

			int totalRows = roleService.queryRoleCount(parameter);
			resultObj.put("errcode", ErrCode.success);
			resultObj.put("totalRows", totalRows);
			resultObj.put("result", result);
			
			return resultObj.toJSONString();			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询角色出现异常");
		}
	}
	
	@RequestMapping(value = "/queryRolePermi", method = RequestMethod.POST, produces = PubFunction.DATA_CODE)
	public Object queryRolePermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);

			// 数据校验
			if (!JsonParamValidator.roleQueryVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

		
			// 数据查询，成功后返回.
			List<RolePermission> result = rolePermiService.queryRolePermission(parameter);
			for (int i = 0; i < result.size(); i++) {
				result.get(i).setDisused(null);
				result.get(i).setRole_code(null);
				result.get(i).setListRows(null);
				result.get(i).setPage(null);
			}		

			int totalRows = rolePermiService.queryRolePermiCount(parameter);
			resultObj.put("errcode", ErrCode.success);
			resultObj.put("totalRows", totalRows);
			resultObj.put("result", result);
			
			return resultObj.toJSONString();			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询角色菜单权限出现异常");
		}
	}

}
