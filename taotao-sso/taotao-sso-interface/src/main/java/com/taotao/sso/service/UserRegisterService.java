package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by Administrator on 2018/4/27.
 */
public interface UserRegisterService {

    TaotaoResult checkUserInfo(String param,int type);
}
