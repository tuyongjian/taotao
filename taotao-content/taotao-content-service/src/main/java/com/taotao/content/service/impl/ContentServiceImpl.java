package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by len on 2018/4/25.
 */
@Service
public class ContentServiceImpl implements ContentService{


    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public TaotaoResult addContent(TbContent content) {
        //补全pojo的属性
        content.setCreated( new Date());
        content.setUpdated(new Date());
        //插入到内容表
        contentMapper.insert(content);
        return TaotaoResult.ok();
    }

    @Override
    public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
            //分页处理
            PageHelper.startPage(page,rows);
            //查询
            List<TbContent> list = contentMapper.selectByCategoryId(categoryId);
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
