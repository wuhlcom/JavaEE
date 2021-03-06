/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午11:02:18 * 
*/
package com.dazk.controller;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;
import com.dazk.common.util.PubUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.db.model.User;
import com.dazk.service.RolePermissionService;
import com.dazk.service.UserService;
import com.dazk.validator.FieldLimit;
import com.dazk.validator.MenuValidator;
import com.dazk.validator.PubParamValidator;
import com.dazk.validator.TokenValidator;
import com.dazk.validator.UserValidator;

@RestController
@RequestMapping("/user")

public class UserController {
	public final static Logger logger = LoggerFactory.getLogger(UserController.class);
	// @Autowired
	// private StringRedisTemplate stringRedisTemplate;

	@Resource
	private UserService userService;

	@Resource
	private RolePermissionService rolePermiService;

	// private static RestTemplate restTemplate = new RestTemplate();

	// @Autowired
	// private ValueOperations<String, Object> valueOperations;
	//
	// @Autowired
	// private HashOperations<String, String, Object> hashOperations;
	//
	// @Autowired
	// private RedisTemplate<String, Object> redisTemplate;
	//

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

			// 当传入parent_user时使用当前用户的id做为parent_user
			JSONObject parameter = JSON.parseObject(requestBody);
			if (RegexUtil.isNull(parameter.getString("parent_user"))) {
				parameter.put("parent_user", rsToken.getString("userid"));
			}

			// 如果角色ID为空就传入当前用户属角色的ID-----不再自动添加角色----
			// if (RegexUtil.isNull(parameter.getString("role_id"))) {
			// parameter.put("role_id", rsToken.getString("role"));
			// }

			// 数据校验
			ResultErr paramsVal = UserValidator.userVal(parameter);
			if (paramsVal.getErrcode() != 10001) {
				// 非法数据，返回错误码
				return paramsVal;
			}

			// 数据入库，成功后返回.
			int res = userService.addUser(parameter);
			if (res == 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(), "账号已注册");
			} else if (res == -3) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "邮箱已注册");
			} else if (res == -4) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "电话已注册");
			} else if (res == -5) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "身份证已注册");
			} else if (res == -6) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "用户添加失败");
			} else if (res == -7) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "超级管理员权限添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "添加用户时出现异常");
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

			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("parent_user", rsToken.getString("userid"));
			if (!UserValidator.isUserId(parameter.getString("id"), FieldLimit.MENU_CODE_MIN,
					FieldLimit.MENU_CODE_MAX)) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "非法用户id编号");
			}
			// 数据入库，成功后返回.
			int res = userService.delUser(parameter);
			if (res >= 1) {
				// this.hashOperations.delete("loginhash", token);
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "删除时程序出错");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "删除数据不存在");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "删除用户时出现异常");
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

			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("parent_user", rsToken.getString("userid"));
			// 数据校验
			ResultErr paramsVal = UserValidator.updateVal(parameter);
			if (paramsVal.getErrcode() != 10001) {
				// 非法数据，返回错误码
				return paramsVal;
			}

			// 数据入库，成功后返回.
			int res = userService.updateUser(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "更新用户出错");
			} else if (res == -2) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "用户已注册");
			} else if (res == -3) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "邮箱已注册");
			} else if (res == -4) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "电话已注册");
			} else if (res == -5) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "身份证已注册");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "用户不存在");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "更新用户时出现异常");
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
			parameter.put("parent_user", rsToken.getLong("userid"));
			if (!UserValidator.queryUserVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			JSONObject resultObj = new JSONObject();
			// 数据查询，成功后返回.
			List<User> result = userService.queryUser(parameter);
			Integer totalRows = userService.queryUserCount(parameter);
			System.out.println(totalRows);
			resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
			resultObj.put("totalRows", totalRows);
			resultObj.put("result", result);
			return resultObj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询用户时出现异常");
		}
	}

	@RequestMapping(value = "/resetPasswd", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object resetPasswd(HttpServletRequest request, HttpServletResponse response,
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

			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("parent_user", rsToken.getLong("userid"));
			ResultErr paramsVal = UserValidator.resetVal(parameter);
			if (paramsVal.getErrcode() != 10001) {
				// 非法数据，返回错误码
				return paramsVal;
			}

			JSONObject resultObj = new JSONObject();
			// 数据查询，成功后返回.
			int res = userService.resetPasswd(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "重置用户密码失败");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "重置密码时出现异常");
		}
	}

}
