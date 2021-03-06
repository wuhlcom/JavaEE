/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月27日 上午9:21:11 * 
*/
package com.dazk.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.errcode.ResultErr;
import com.dazk.common.errcode.ResultStatusCode;
import com.dazk.common.util.PubUtil;
import com.dazk.db.model.Role;
import com.dazk.service.RolePermissionService;
import com.dazk.service.RoleService;
import com.dazk.validator.FieldLimit;
import com.dazk.validator.RoleValidator;
import com.dazk.validator.TokenValidator;

@Controller
@RestController
@RequestMapping("/role")
public class RoleController {
	public final static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Resource
	private RoleService roleService;

	@Resource
	private RolePermissionService rolePermiService;

	@RequestMapping(value = "/addRole", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addRole(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

			// 权限验证
			// post head token
			String token = request.getHeader("token");
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("user_id", rsToken.getString("userid"));
			// 数据校验
			ResultErr paramsVal = RoleValidator.roleVal(parameter);
			if (paramsVal.getErrcode() != 10001) {
				// 非法数据，返回错误码
				return paramsVal;
			}

			// 数据入库，成功后返回.
			int res = roleService.addRole(parameter);
			if (res == 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(), "角色名已存在!");
			} else if (res == -2) {
				return new ResultErr(ResultStatusCode.REPETITION_ERR.getCode(), "角色编码已存在!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), ResultStatusCode.ROUTINE_ERR.getErrmsg());
		} finally {

		}
		return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
	}

	@RequestMapping(value = "/delRole", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object delRole(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);

			// 权限验证
			// post head token
			String token = request.getHeader("token");
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}


			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("user_id", rsToken.getString("userid"));
			if (!RoleValidator.isRoleCode(parameter.getString("id"), FieldLimit.ROLE_ID_MIN, FieldLimit.ROLE_ID_MAX)) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), "非法角色编号");
			}
			// 数据入库，成功后返回.
			int res = roleService.delRole(parameter);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == -1) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "角色已被使用");
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.NODATA_ERR.getCode(), "删除数据不存在");
			}
			return new ResultErr(ResultStatusCode.UNKNOW_ERR.getCode(), ResultStatusCode.UNKNOW_ERR.getErrmsg());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), ResultStatusCode.ROUTINE_ERR.getErrmsg());
		}
	}

	@RequestMapping(value = "/updateRole", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object updateRole(HttpServletRequest request, HttpServletResponse response,
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

			JSONObject parameter = JSON.parseObject(requestBody);
			parameter.put("user_id", rsToken.getString("userid"));
			// 数据校验
			ResultErr paramsVal = RoleValidator.roleVal(parameter);
			if (paramsVal.getErrcode() != 10001) {
				// 非法数据，返回错误码
				return paramsVal;
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

	@RequestMapping(value = "/queryRole", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryRole(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);
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
			// 数据校验
			if (!RoleValidator.roleQueryVal(parameter)) {
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
			resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
			resultObj.put("totalRows", totalRows);
			resultObj.put("result", result);

			return resultObj.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询角色出现异常");
		}
	}

}
