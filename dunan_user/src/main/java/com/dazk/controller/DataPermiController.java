/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年7月29日 下午5:42:42 * 
*/
package com.dazk.controller;

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
import com.dazk.common.util.PubFunction;
import com.dazk.service.DataPermissionService;
import com.dazk.validator.JsonParamValidator;

@RestController
@RequestMapping("/data")
public class DataPermiController {
	@Resource
	private DataPermissionService dataPermiService;

	@RequestMapping(value = "/addDataPermi", method = RequestMethod.POST, produces = PubFunction.DATA_CODE)
	public Object addDataPermi(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {

		try {
			System.out.println("result=" + requestBody);
			// 权限验证
			String token = request.getParameter("token");
			// 根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
			JSONObject parameter = JSON.parseObject(requestBody);
			// 数据校验
			if (!JsonParamValidator.dataPermiVal(parameter)) {
				// 非法数据，返回错误码
				return new ResultErr(ResultStatusCode.PARAME_ERR.getCode(), ResultStatusCode.PARAME_ERR.getErrmsg());
			}

			// 数据入库，成功后返回.
			int res = dataPermiService.addDataPermi(parameter);
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
}
