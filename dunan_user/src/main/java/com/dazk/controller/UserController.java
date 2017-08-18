/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午11:02:18 * 
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
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;
import com.dazk.common.util.PubUtil;
import com.dazk.db.model.User;
import com.dazk.service.RolePermissionService;
import com.dazk.service.UserService;
import com.dazk.validator.FieldLimit;
import com.dazk.validator.JsonParamValidator;
import com.dazk.validator.TokenValidator;
import com.dazk.validator.UserValidator;

@RestController
@RequestMapping("/user")

public class UserController {
	@Resource
	private UserService userService;

	@Resource
	private RolePermissionService rolePermiService;

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addUser(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {

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

			// String uri = request.getRequestURI();
			// Boolean rs = rolePermiService.menuAuth(uri,
			// rsToken.getLong("role"));
			// if (!rs) {
			// return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(),
			// ResultStatusCode.PARAME_ERR.getErrmsg());
			// }

			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("parent_user", rsToken.getString("userid"));
			// 数据校验
			if (!UserValidator.userVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = userService.addUser(parameter);
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

	@RequestMapping(value = "/delUser", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object delUser(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
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

			// String uri = request.getRequestURI();
			// Boolean rs = rolePermiService.menuAuth(uri,
			// rsToken.getLong("role"));
			// if (!rs) {
			// return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(),
			// ResultStatusCode.PARAME_ERR.getErrmsg());
			// }

			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("parent_user", rsToken.getString("userid"));
			if (!UserValidator.isUserId(parameter.getString("id"), FieldLimit.MENU_CODE_MIN,
					FieldLimit.MENU_CODE_MAX)) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "非法用户id编号");
			}
			// 数据入库，成功后返回.
			int res = userService.delUser(parameter);
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

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object updateUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

			// 权限验证
			// post head token
			String token = request.getHeader("token");
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}
			//
			// String uri = request.getRequestURI();
			// Boolean rs = rolePermiService.menuAuth(uri,
			// rsToken.getLong("role"));
			// if (!rs) {
			// return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(),
			// ResultStatusCode.PARAME_ERR.getErrmsg());
			// }

			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("parent_user", rsToken.getString("userid"));
			// 数据校验
			if (!UserValidator.userVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = userService.updateUser(parameter);
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

	@RequestMapping(value = "/queryUser", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryUser(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

			// 权限验证
			// post head token
			String token = request.getHeader("token");
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			// String uri = request.getRequestURI();
			// Boolean rs = rolePermiService.menuAuth(uri,
			// rsToken.getLong("role"));
			// if (!rs) {
			// return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(),
			// ResultStatusCode.PARAME_ERR.getErrmsg());
			// }

			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("parentUser", rsToken.getLong("userid"));
			if (!UserValidator.queryUserVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			JSONObject resultObj = new JSONObject();
			// 数据查询，成功后返回.
			List<User> result = userService.queryUser(parameter);
			for (int i = 0; i < result.size(); i++) {
				result.get(i).setIsdel(null);	
				result.get(i).setDisused(null);			
				result.get(i).setSex(null);			
			}
            Integer totalRows = userService.queryUserCount(parameter);		
            resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
			resultObj.put("totalRows", totalRows);
			resultObj.put("result", result);
			return resultObj.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询用户时出现异常");
		}
	}

	@RequestMapping(value = "/queryUserByRole", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryUserByRole(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

			// 权限验证
			// post head token
			String token = request.getHeader("token");
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			// String uri = request.getRequestURI();
			// Boolean rs = rolePermiService.menuAuth(uri,
			// rsToken.getLong("role"));
			// if (!rs) {
			// return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(),
			// ResultStatusCode.PARAME_ERR.getErrmsg());
			// }

			Object resultObj = null;
			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("parent_user", rsToken.getString("userid"));
			if (!UserValidator.queryUserByRoleVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据查询，成功后返回.
			resultObj = userService.queryUserByRole(parameter);
			if (resultObj == null) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), ResultStatusCode.NODATA_ERR.getErrmsg());
			}
			return resultObj;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询角色下用户时出现异常");
		}
	}
}
