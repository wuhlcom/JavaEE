/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月31日 下午7:56:16 * 
*/
package com.dazk.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.dazk.common.util.RegexUtil;
import com.dazk.db.model.RolePermission;
import com.dazk.service.RolePermissionService;
import com.dazk.validator.FieldLimit;
import com.dazk.validator.MenuValidator;
import com.dazk.validator.RolePermiValidator;
import com.dazk.validator.RoleValidator;
import com.dazk.validator.TokenValidator;

@RestController
@RequestMapping("/role")
public class RolePermiController {
	public final static Logger logger = LoggerFactory.getLogger(RolePermiController.class);

	@Resource
	private RolePermissionService rolePermiService;

	@RequestMapping(value = "/addRolePermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addRolePermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

			// 权限验证
			// String token = request.getParameter("token");
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
			// 数据校验
			if (!RolePermiValidator.rolePermiVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = rolePermiService.addRolePermi(parameter);
			if (res == 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(), "添加角色权限失败!");
			} else if (res == -2) {
				return new ResultErr(ResultStatusCode.ROLE_NOT_EXIST.getCode(),
						ResultStatusCode.ROLE_NOT_EXIST.getErrmsg());
			} else if (res == -3) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "找不到菜单!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(),"添加角色菜单权限时出现异常");
		} finally {

		}
		return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
	}

	@RequestMapping(value = "/delRolePermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object delRolePermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

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
			if (!RolePermiValidator.rolePermiVal(parameter)) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "非法角色编号");
			}
			// 数据入库，成功后返回.
			int res = rolePermiService.delRolePermi(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "删除角色出错");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "角色不存在");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "删除角色菜单权限时出现异常");
		}
	}

	@RequestMapping(value = "/updateRolePermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object updateRole(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

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
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "更新角色信息出错");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "角色不存在");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "更新角色菜单时出现异常");
		}
	}

	@RequestMapping(value = "/queryRolePermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryRolePermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

			// 权限验证
			// post head token
			String token = request.getHeader("token");
			// String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("user_id", rsToken.getString("userid"));
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
			System.out.println("Request=" + requestBody);

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

			// 数据校验
			ResultErr paramsVal = RolePermiValidator.roleMenuVal(parameter);
			if (paramsVal.getErrcode() != 10001) {
				// 非法数据，返回错误码
				return paramsVal;
			}

			// 数据入库，成功后返回.
			int res = rolePermiService.addRoleMenu(parameter);
			if (res == 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(),
						"请勿添加重复角色");
			} else if (res == -2) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "角色添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "添加角色菜单权限出现异常");
		} finally {

		}
		return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
	}

	@RequestMapping(value = "/delRoleMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object delRoleMenu(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

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
			if (!RolePermiValidator.roleMenuDelVal(parameter)) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}
			// 数据入库，成功后返回.
			int res = rolePermiService.delRoleMenu(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "删除时程序出错");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "角色不存在");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), ResultStatusCode.ROUTINE_ERR.getErrmsg());
		}
	}

	@RequestMapping(value = "/updateRoleMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object updateRoleMenu(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

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
			// 数据校验
			if (!RolePermiValidator.roleMenuUpdateVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = rolePermiService.updateRoleMenu(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "角色不存在或角色更新失败");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "更新角色菜单权限出现异常");
		}
	}

	@RequestMapping(value = "/queryRoleMenu", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryRoleMenu(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

			// 权限验证
			// post head token
			String token = request.getHeader("token");
			// String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("user_id", rsToken.getString("userid"));
			String roleId = parameter.getString("role_id");
			if (RegexUtil.isNull(roleId)) {
				parameter.put("role_id", rsToken.getString("role"));
			}
			// 数据校验
			if (!RolePermiValidator.rolePermiQueryVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			Map result = rolePermiService.queryRoleMenu(parameter);

			if (result == null) {
				return new ResultErr(ResultStatusCode.ROLE_NOT_EXIST.getCode(),
						"角色不存在");
			}
			resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
			resultObj.put("result", result.get("menus"));
			resultObj.put("uris", result.get("uris"));
			resultObj.put("furis", result.get("furis"));
			resultObj.put("urisAll", result.get("urisAll"));
			resultObj.put("furisAll", result.get("furisAll"));
			return resultObj.toJSONString();

		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询角色菜单权限出现异常");
		}
	}
}
