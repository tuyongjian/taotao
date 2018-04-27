package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * Created by Administrator on 2018/4/27.
 * 用户注册服务
 */
public interface UserRegisterService {

    /**
     * 检查是否用户信息是否正确
     * @param param
     * @param type
     * @return
     */
    TaotaoResult checkUserInfo(String param,int type);
    /**
     * 添加用户
     * @param tbUser
     * @return
     */
    TaotaoResult createUser(TbUser tbUser);
}
