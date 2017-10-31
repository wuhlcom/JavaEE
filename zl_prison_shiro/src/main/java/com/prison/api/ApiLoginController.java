/** 
* @author :wuhongliang wuhongliang@zhilutec.com
* @version :2017年10月19日 上午11:01:44 * 
* @description :单独创建API文档
*/ 
package com.prison.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api")
@Api("登录接口")
public class ApiLoginController {
	
//	@Autowired
//    private UserService userService;
//    @Autowired
//    private TokenService tokenService;
//
//    /**
//     * 登录
//     */
//    @IgnoreAuth
//    @PostMapping("login")
//    @ApiOperation(value = "登录",notes = "登录说明")
//    @ApiImplicitParams({
//        @ApiImplicitParam(paramType = "query", dataType="string", name = "mobile", value = "手机号", required = true),
//        @ApiImplicitParam(paramType = "query", dataType="string", name = "password", value = "密码", required = true)
//    })
//    public R login(String mobile, String password){
//        Assert.isBlank(mobile, "手机号不能为空");
//        Assert.isBlank(password, "密码不能为空");
//
//        //用户登录
//        long userId = userService.login(mobile, password);
//
//        //生成token
//        Map<String, Object> map = tokenService.createToken(userId);
//
//        return R.ok(map);
//    }

}
