package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2018/4/27.
 */
@Controller
public class UserController {

    @Autowired
    private UserRegisterService userRegisterService;

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
            @RequestParam(value = "email")String email,
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














}
