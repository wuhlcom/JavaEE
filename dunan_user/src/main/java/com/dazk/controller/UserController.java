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
import com.dazk.service.UserService;
import com.dazk.validator.FieldLimit;
import com.dazk.validator.JsonParamValidator;
import com.dazk.validator.UserValidator;

@RestController
@RequestMapping("/user")

public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addUser(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		// 权限验证
		String token = request.getParameter("token");
		// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
		try {
			System.out.println("result=" + requestBody);
			JSONObject parameter = JSON.parseObject(requestBody);
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
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

			JSONObject parameter = JSON.parseObject(requestBody);
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
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续

			JSONObject parameter = JSON.parseObject(requestBody);
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
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续	
			JSONObject parameter = JSON.parseObject(requestBody);

			if (!UserValidator.queryUserVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据查询，成功后返回.
			List<User> result = (List<User>) userService.queryUser(parameter);		
			for (int i = 0; i < result.size(); i++) {
				result.get(i).setIsdel(null);
				result.get(i).setCreated_at(null);
				result.get(i).setListRows(null);
				result.get(i).setPage(null);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询用户时出现异常");
		}
	}

	@RequestMapping(value = "/queryUserByRole", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryUserByRole(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {	
		try {
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			Object resultObj = null;
			JSONObject parameter = JSON.parseObject(requestBody);

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
