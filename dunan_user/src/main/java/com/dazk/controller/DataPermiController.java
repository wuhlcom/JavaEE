/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月29日 下午5:42:42 * 
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
import com.dazk.common.util.RegexUtil;
import com.dazk.db.model.DataPermission;
import com.dazk.service.DataPermissionService;
import com.dazk.service.RolePermissionService;
import com.dazk.validator.DataPermiValidator;
import com.dazk.validator.PubParamValidator;
import com.dazk.validator.TokenValidator;
import com.dazk.validator.DataPermiValidator;

@RestController
@RequestMapping("/data")
public class DataPermiController {
	@Resource
	private DataPermissionService dataPermiService;
	@Resource
	private RolePermissionService rolePermiService;

	@RequestMapping(value = "/addDataPermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addDataPermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {

		try {
			System.out.println("Request=" + requestBody);
			// 权限验证
			// String token = request.getParameter("token");
			// post head token
			String token = request.getHeader("token");
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
			parameter.put("user_id", rsToken.getString("userid"));
			// 数据校验
			if (!DataPermiValidator.dataPermiVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = dataPermiService.addDataPermi(parameter);
			if (res == 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "添加数据失败");
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

	@RequestMapping(value = "/addDataPermiBatch", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object addDataPermiBatch(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {

		try {
			System.out.println("Request=" + requestBody);
			// 权限验证
			// String token = request.getParameter("token");
			// post head token
			String token = request.getHeader("token");
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
			// String user_id = parameter.getString("userid");
			// if (user_id == null||user_id=="") {
			// parameter.put("user_id", rsToken.getString("userid"));
			// }

			// 数据校验
			if (!DataPermiValidator.dataPermiBatchVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = dataPermiService.addDataPermiBatch(parameter);
			System.out.println(res);
			if (res >= 1) {
				return new ResultErr(ResultStatusCode.SUCCESS.getCode(), ResultStatusCode.SUCCESS.getErrmsg());
			} else if (res == 0) {
				return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "添加数据失败");
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

	@RequestMapping(value = "/delDataPermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object delDataPermi(HttpServletRequest request, HttpServletResponse response,
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

			// String uri = request.getRequestURI();
			// Boolean rs = rolePermiService.menuAuth(uri,
			// rsToken.getLong("role"));
			// if (!rs) {
			// return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(),
			// ResultStatusCode.PARAME_ERR.getErrmsg());
			// }

			JSONObject parameter = JSON.parseObject(requestBody);
			String user_id = parameter.getString("user_id");
			if (RegexUtil.isNull(user_id)) {
				parameter.put("user_id", rsToken.getString("userid"));
			}
			// 数据校验
			if (!DataPermiValidator.dataPermiDelVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			if (!DataPermiValidator.dataPermiDelVal(parameter)) {
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = dataPermiService.delDataPermi(parameter);
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

	@RequestMapping(value = "/updateDataPermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object updateDataPermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);
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
			parameter.put("user_id", rsToken.getString("userid"));
			// 数据校验
			if (!DataPermiValidator.dataPermiUpdateVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = dataPermiService.updateDataPermi(parameter);
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

	@RequestMapping(value = "/queryData", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryData(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);
			String token = request.getHeader("token");
			// String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}		

			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);					
			if (!DataPermiValidator.dataQueryVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据查询，成功后返回.
			List<DataPermission> result = dataPermiService.queryData(parameter);		
			for (int i = 0; i < result.size(); i++) {
				result.get(i).setListRows(null);
				result.get(i).setPage(null);
			}

			int totalRows = dataPermiService.queryDataPermiCount(parameter);
			resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
			resultObj.put("totalRows", totalRows);
			resultObj.put("result", result);
			return resultObj.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询用户数据权限时出现异常");
		}
	}
	
	@RequestMapping(value = "/queryDataPermi", method = RequestMethod.POST, produces = PubUtil.DATA_CODE)
	public Object queryDataPermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		try {
			System.out.println("Request=" + requestBody);
			String token = request.getHeader("token");
			// String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject rsToken = TokenValidator.getRsToken(token);
			if (rsToken.getInteger("status") == 0) {
				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
			}

			JSONObject resultObj = new JSONObject();
			JSONObject parameter = JSON.parseObject(requestBody);					
			if (!DataPermiValidator.dataPermiQueryVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据查询，成功后返回.
			List<String> result = dataPermiService.queryDataPermi(parameter);
			resultObj.put("errcode", ResultStatusCode.SUCCESS.getCode());
			resultObj.put("result", result);
			return resultObj.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询用户数据权限时出现异常");
		}
	}

}
