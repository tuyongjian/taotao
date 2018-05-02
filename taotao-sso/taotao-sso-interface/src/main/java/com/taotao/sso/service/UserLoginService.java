package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by len on 2018/5/2.
 * 登录接口
 */
public interface UserLoginService {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    TaotaoResult userLogin(String username,String password)throws Exception;

    /**
     * 通过token查询用户信息
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 退出登录，其实就是删除redis里面的数据
     * @param token
     * @return
     */
    TaotaoResult loginOut(String token);

}
