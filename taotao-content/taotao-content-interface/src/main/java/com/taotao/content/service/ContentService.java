package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by len on 2018/4/25.
 */
public interface ContentService {

    /**
     * 添加
     * @param content
     * @return
     */
    TaotaoResult addContent(TbContent content);

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGridResult getContentList(long categoryId,int page, int rows);


    List<TbContent> getContentByCid(long cid);
}
