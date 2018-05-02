package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserLoginService;
import com.taotao.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/4/27.
 */
@Controller
public class UserController {

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserLoginService userLoginService;

    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    @RequestMapping(value="/user/check/{param}/{type}", method= RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkUserInfo(
            @PathVariable String param, @PathVariable Integer type) {
        TaotaoResult result = userRegisterService.checkUserInfo(param, type);
        return result;
    }

    @RequestMapping(value = "/user/register")
    @ResponseBody
    public TaotaoResult registerUser(
            @RequestParam(value = "email" ,required = false)String email,
            @RequestParam(value = "password")String password,
            @RequestParam(value = "username")String username,
            @RequestParam(value = "phone",required = false)String phone){
        TbUser user = new TbUser();
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUsername(username);
        TaotaoResult result = userRegisterService.createUser(user);
        return result;
    }


    /**
     * 登录
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/user/login", method=RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(String username, String password,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
        TaotaoResult taotaoResult = userLoginService.userLogin(username, password);
        // 取出token
        if(taotaoResult.getStatus()==200){
            String token = taotaoResult.getData().toString();
            // 在返回结果之前，设置cookie(即将token写入cookie)
            // 1.cookie怎么跨域？
            // 2.如何设置cookie的有效期？
            CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
        }
        // 返回结果
        return taotaoResult;
    }


    /**
     * 通过token查询用户信息 支持jsonp
     * @param token
     * @return
     */
    @RequestMapping(value = "user/token",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@RequestParam(value = "token")String token,
                                       @RequestParam(value = "callback",required = false)String callback){
        TaotaoResult result = this.userLoginService.getUserByToken(token);
        if(StringUtils.isNotBlank(callback)){
            // 客户端为jsonp请求，需要返回js代码
            String jsonResult = callback + "(" + JsonUtils.objectToJson(result) + ");";
            return jsonResult; // 统一返回字符串
        }
        return JsonUtils.objectToJson(result); // 统一返回字符串
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @RequestMapping(value = "user/loginout",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult loginout(@RequestParam(value = "token")String token){
        return this.userLoginService.loginOut(token);
    }

}
