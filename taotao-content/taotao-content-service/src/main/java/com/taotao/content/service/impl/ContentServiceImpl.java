package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by len on 2018/4/25.
 */
@Service
public class ContentServiceImpl implements ContentService{


    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;

    @Override
    public TaotaoResult addContent(TbContent content) {
        //补全pojo的属性
        content.setCreated( new Date());
        content.setUpdated(new Date());
        //插入到内容表
        contentMapper.insert(content);
        //删除 redis 缓存
        jedisClient.hdel(CONTENT_KEY,content.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
        // 查询数据库之前，先查询缓存，并且添加缓存不能影响正常业务逻辑
        List<TbContent> list = null;
        try {
            String json = jedisClient.hget(CONTENT_KEY, categoryId + "");
            // 判断是否命中缓存，判断json字符串是否为null或""
            if (StringUtils.isNotBlank(json)) {
                // 把这个json转换成List集合
                list = JsonUtils.jsonToList(json, TbContent.class);
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
            //分页处理
            PageHelper.startPage(page,rows);
            //查询
            if(CollectionUtils.isEmpty(list)){
                list = contentMapper.selectByCategoryId(categoryId);
                // 向缓存中保存结果，并且添加缓存不能影响正常业务逻辑
                try {
                    jedisClient.hset(CONTENT_KEY, categoryId + "", JsonUtils.objectToJson(list));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //创建返回结果对象
            EasyUIDataGridResult result = new EasyUIDataGridResult();
            result.setRows(list);
            //取总记录数
            PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
            result.setTotal(pageInfo.getTotal());
            return result;
    }

    @Override
    public List<TbContent> getContentByCid(long cid) {
        return null;
    }
}
