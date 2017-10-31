/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月20日 上午11:45:06 * 
*/
package com.enviroment.controller;

import java.util.List;

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
import com.enviroment.common.errcode.ResultErr;
import com.enviroment.common.errcode.ResultStatusCode;
import com.enviroment.common.validator.GasValidator;
import com.enviroment.common.validator.TokenValidator;
import com.enviroment.service.GasService;



@RestController
@RequestMapping("/gas")
public class GasController {
	@Resource
	private GasService gasService;
	public final static Logger logger = LoggerFactory.getLogger(GasController.class);

	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object query(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
		try {
			logger.debug("Request=" + requestBody);	
			//token验证
//			String token = request.getHeader("token");
//			JSONObject rsToken = TokenValidator.getRsToken(token);
//			if (rsToken.getInteger("status") == 0) {
//				return new ResultErr(ResultStatusCode.TOKEN_ERR.getCode(), ResultStatusCode.TOKEN_ERR.getErrmsg());
//			}

			JSONObject parameter = JSON.parseObject(requestBody);
//			parameter.put("user_id", rsToken.getString("userid"));

			// 数据校验
			ResultErr paramsVal = GasValidator.queryValidate(parameter);
			if (paramsVal.getErrcode() != 10001) {
				// 非法数据，返回错误码
				return paramsVal;
			}	
			return gasService.result(parameter);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultErr(ResultStatusCode.ROUTINE_ERR.getCode(), "查询燃气表数据出现异常");
		}
	}
}
