package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by len on 2018/5/2.
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_PRE}")
    private String SESSION_PRE;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;




    @Override
    public TaotaoResult userLogin(String username, String password) {

        // 判断用户名和密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }

        // 校验密码，密码要进行md5加密后再校验
        TbUser user = list.get(0);
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return TaotaoResult.build(400, "用户名或密码错误");
        }
        //生成一个token
        String token = UUID.randomUUID().toString();
        //把用户信息放到redis里面去，但是不能把密码放入，
        //token就是key value就是用户对象的值
        user.setPassword(null);
        jedisClient.set(SESSION_PRE+":"+token, JsonUtils.objectToJson(user));
        //设置过期时间
        jedisClient.expire(SESSION_PRE+":"+token,SESSION_EXPIRE);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(SESSION_PRE+":"+token);
        if (StringUtils.isBlank(json)) {
            return TaotaoResult.build(400, "用户登录已经过期");
        }
        //重置Session的过期时间
        jedisClient.expire(SESSION_PRE + ":" + token, SESSION_EXPIRE);
        //把json转换成User对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(user);
    }

    @Override
    public TaotaoResult loginOut(String token) {
        jedisClient.expire(SESSION_PRE+":"+token,0);
        return TaotaoResult.ok();
    }
}
