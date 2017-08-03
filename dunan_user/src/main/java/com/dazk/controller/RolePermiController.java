/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午7:56:16 * 
*/
package com.dazk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;
import com.dazk.common.util.PubUtil;
import com.dazk.db.model.RolePermission;
import com.dazk.service.RolePermissionService;
import com.dazk.validator.FieldLimit;
import com.dazk.validator.RolePermiValidator;
import com.dazk.validator.RoleValidator;

@RestController
@RequestMapping("/role")
public class RolePermiController {
	@Resource
	private RolePermissionService rolePermiService;

	@RequestMapping(value = "/addRolePermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addRolePermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			System.out.println("result=" + requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			// 数据校验
			if (!RolePermiValidator.rolePermiVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = rolePermiService.addRolePermi(parameter);
			if (res == 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(),
						ResultStatusCode.REPETITION_ERR.getErrmsg());
			} else if (res == -2) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), ResultStatusCode.NODATA_ERR.getErrmsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), ResultStatusCode.ROUTINE_ERR.getErrmsg());
		} finally {

		}
		return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
	}

	@RequestMapping(value = "/delRolePermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object delRolePermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

			JSONObject parameter = JSON.parseObject(requestBody);
			// if
			// (!RoleValidator.isRoleCode(parameter.getString("code"),FieldLimit.ROLE_CODE_MIN,FieldLimit.ROLE_CODE_MAX))
			// {
			if (!RolePermiValidator.rolePermiVal(parameter)) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "非法角色编号");
			}
			// 数据入库，成功后返回.
			int res = rolePermiService.delRolePermi(parameter);
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

	@RequestMapping(value = "/updateRolePermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object updateRole(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

			JSONObject parameter = JSON.parseObject(requestBody);
			// 数据校验
			if (!RolePermiValidator.rolePermiVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = rolePermiService.updateRolePermi(parameter);
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

	@RequestMapping(value = "/queryRolePermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryRolePermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);

			// 数据校验
			if (!RolePermiValidator.rolePermiQueryVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据查询，成功后返回.
			List<RolePermission> result = rolePermiService.queryRolePermi(parameter);
			for (int i = 0; i < result.size(); i++) {
				result.get(i).setDisused(null);
				result.get(i).setRole_id(null);
				result.get(i).setListRows(null);
				result.get(i).setPage(null);
			}

			int totalRows = rolePermiService.queryRolePermiCount(parameter);
			resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
			resultObj.put("totalRows", totalRows);
			resultObj.put("result", result);

			return resultObj.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询角色菜单权限出现异常");
		}
	}

	@RequestMapping(value = "/addRoleMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addRoleMenu(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			System.out.println("result=" + requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
			// 数据校验
			if (!RolePermiValidator.roleMenuVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = rolePermiService.addRoleMenu(parameter);
			if (res == 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(),
						ResultStatusCode.REPETITION_ERR.getErrmsg());
			} else if (res == -2) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "角色添加失败");
			}

		} catch (

		Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), ResultStatusCode.ROUTINE_ERR.getErrmsg());
		} finally {

		}
		return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
	}

	@RequestMapping(value = "/queryRoleMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryRoleMenu(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);

			// 数据校验
			if (!RolePermiValidator.rolePermiQueryVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据查询，成功后返回.
			List result = rolePermiService.queryRoleMenu(parameter);
			resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
			resultObj.put("result", result);
			System.out.println(resultObj);
			return resultObj.toJSONString();
			// return JSON.toJSONString(resultObj,
			// SerializerFeature.DisableCircularReferenceDetect);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询角色菜单权限出现异常");
		}
	}
}
